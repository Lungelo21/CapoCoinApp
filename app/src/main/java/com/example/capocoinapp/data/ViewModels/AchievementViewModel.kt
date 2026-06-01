package com.example.capocoinapp.data.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.capocoinapp.Services.AchievementService
import com.example.capocoinapp.Supabase.SupabaseClient
import com.example.capocoinapp.Utils.isInternetAvailable
import com.example.capocoinapp.data.dto.AchievementsDTO
import com.example.capocoinapp.data.dto.toEntity
import com.example.capocoinapp.data.entities.Achievements
import com.example.capocoinapp.data.entities.User
import com.example.capocoinapp.data.entities.toDTO
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AchievementViewModel(
    val achievementService: AchievementService,
    val application: Application
) : ViewModel() {


    var currentUserID: String = ""
    private set

    private val userAchievements = listOf(
        /*
        * Link: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-pair/
        * Author: Kotlin Programming Language
        * Date Accessed: 1/06/2026
        * */

        Pair("Breaking the Ice", "Log your first transaction"),
        Pair("Baby Steps", "Create your first 5 categories"),
        Pair("Night Shift", "Log a transaction between 10PM and 4AM"),
        Pair("Saver's Streak", "Log 10 individual income Transactions"),
        Pair("The Century Club", "Log 100 total transactions"),
        Pair("Big Spender", "Log a single transaction worth over R500")

    )

   init {
       viewModelScope.launch {
           val sessionEmail = SupabaseClient.client.auth.currentUserOrNull()?.email

           if(sessionEmail != null) {
               try {
                   val foundUser = SupabaseClient.client.postgrest["users"]
                       .select {
                           filter {
                               eq("email", sessionEmail)
                           }
                       }
                       .decodeSingle<User>()

                   currentUserID = foundUser.id
                   Log.d(
                       "AchievementVM",
                       "Successfully fetched the current user's id from Postgrest users table"
                   )
               } catch (e: Exception)
               {
                   Log.e("AchievementVM", "Issue finding user from Users")
               }
           }

           val userID = currentUserID

           val localAchievements = achievementService.getAllAchievements(userID).first()

           // Check if achievements exist locally in Room for this user ID
           if(localAchievements.isEmpty())
           {
               Log.d("AchievementVM", "Local Achievements table is empty. Populating Achievements")
               userAchievements.forEach { achievement ->
                   achievementService.createOrUpdateAchievement(
                       Achievements(
                           achievementTitle = achievement.first,
                           description = achievement.second,
                           isUnlocked = 0,
                           dateUnlocked = null,
                           userID = userID
                       )
                   )
               }
           }

           //Fetching the remotely stored data
           try {
               if(application.isInternetAvailable())
               {
                   val supabaseAchievementsDTOs = SupabaseClient.client.postgrest["achievements"]
                       .select {
                           filter {
                               eq("userID", userID)
                           }
                       }
                       .decodeList<AchievementsDTO>()

                   if(supabaseAchievementsDTOs.isNotEmpty())
                   {
                       Log.d("AchievementVM", "Pulled ${supabaseAchievementsDTOs.size} records from Supabase")

                       supabaseAchievementsDTOs.forEach { dto ->
                           achievementService.createOrUpdateAchievement(dto.toEntity())
                       }
                   }
               }
           }
           catch (e: Exception)
           {
               Log.e("AchievementVM", "Remote DB initialisation error: ${e.message}")
           }
       }
   }

    fun unlockAchievement(achievementTitle: String)
    {
        viewModelScope.launch {
            val userID = currentUserID

            val targetedAchievement = achievementService.getAllAchievements(userID).first()
                .find { it.achievementTitle == achievementTitle && it.isUnlocked == 0 }

            if(targetedAchievement != null)
            {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)

                val unlockedCopy = targetedAchievement.copy(isUnlocked = 1, dateUnlocked = date)

                //Updating the local achievements table
                achievementService.createOrUpdateAchievement(unlockedCopy)

                Log.d("AchievementCheck", "Achievement Completed: $achievementTitle on $date")

                viewModelScope.launch {
                    var uploaded = false

                    while (!uploaded)
                    {
                        if(application.isInternetAvailable())
                        {
                            try {
                                SupabaseClient.client.postgrest["achievements"].upsert(unlockedCopy.toDTO())

                                Log.d("SyncCheck", "Successfully synced Completed Achievement '$achievementTitle' to Supabase storage")
                                uploaded = true
                            }
                            catch (e: Exception)
                            {
                                Log.e("SyncCheck", "Failed to update remote storage, retrying after 10 seconds. ${e.message}")

                                delay(10000)//Delayed 10 seconds
                            }
                        }
                        else
                        {
                            Log.d("SyncCheck", "Connection error. Waiting 5 seconds before trying to upload updated achievements")
                            delay(5000)//Delay 5 seconds
                        }
                    }
                }
            }
        }
    }

    fun getAllAchievements(userID: String): Flow<List<Achievements>>
    {
        return achievementService.getAllAchievements(userID)
    }
}

class AchievementViewModelFactory(private val service: AchievementService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(AchievementViewModel::class.java)) {

            // Extract the Application context via the APPLICATION_KEY
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

            @Suppress("UNCHECKED_CAST")
            return AchievementViewModel(service, application) as T
        }
        throw IllegalArgumentException("Error Occurred initializing AchievementViewModel")
    }
}