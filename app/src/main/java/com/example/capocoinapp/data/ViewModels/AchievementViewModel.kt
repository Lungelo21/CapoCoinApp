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

   init {
       viewModelScope.launch {
           var fetchedUserID = false
           while (!fetchedUserID) {

               val sessionEmail = SupabaseClient.client.auth.currentUserOrNull()?.email

               if (sessionEmail != null) {
                   try {
                       if (application.isInternetAvailable()) {
                           val foundUser = SupabaseClient.client.postgrest["users"]
                               .select {
                                   filter {
                                       eq("email", sessionEmail)
                                   }
                               }
                               .decodeSingle<User>()

                           currentUserID = foundUser.id

                           fetchedUserID = true
                           Log.d(
                               "AchievementVM",
                               "Successfully fetched the current user's id from Postgrest users table: ${currentUserID}"
                           )
                       } else {
                           Log.w(
                               "AchievementVM",
                               "No Internet Connection. Attempting user retrieval after 5 seconds."
                           )

                           delay(5000)//Delay by 5 seconds
                       }
                   } catch (e: Exception) {
                       Log.e(
                           "AchievementVM",
                           "Issue finding user from Users table. retrying after 5 seconds. ${e.message}"
                       )

                       delay(5000)//Delay by 5 seconds
                   }
               } else {
                   Log.w(
                       "AchievementVM",
                       "No authenticated users found. Retrying initialization after 5 seconds."
                   )

                   delay(5000)//Delay by 5 seconds
               }
           }
               val userID = currentUserID

               val localAchievements = achievementService.getAllAchievements(userID).first()

               // Check if achievements exist locally in Room for this user ID
               if (localAchievements.isEmpty()) {
                   Log.d(
                       "AchievementVM",
                       "Local Achievements table is empty. Populating Achievements"
                   )

                   //Populating all user achievements for the dedicated user
                   achievementService.populateUserAchievements(userID)

                   //Retrieving all the locally populated achievements
                   val populatedLocalAchievements =
                       achievementService.getAllAchievements(userID).first()

                   //Co routine used to sync the local achievements to supabase
                   viewModelScope.launch {
                       var isSynced = false

                       while (!isSynced) {
                           if (application.isInternetAvailable()) {
                               try {
                                   Log.d("AchievementVM", "Syncing achievements to Supabase")

                                   //mapping the achievements
                                   val achievementsDTOs =
                                       populatedLocalAchievements.map { it.toDTO() }

                                   //Upserting the local achievements to remote
                                   SupabaseClient.client.postgrest["achievements"].upsert(
                                       achievementsDTOs
                                   )

                                   Log.d(
                                       "AchievementVM",
                                       "Successfully synced Supabase with all achievements!"
                                   )

                                   //breaking the loop after successfully syncing with remote
                                   isSynced = true
                               } catch (e: Exception) {
                                   Log.e(
                                       "AchievementVM",
                                       "Sync with remote failed: ${e.message}. Retrying after 10s."
                                   )

                                   delay(10000)//Delay for 10 seconds to retry sync after failure
                               }
                           } else {
                               Log.d(
                                   "AchievementVM",
                                   "Currently Offline. Waiting for internet connection. Retrying sync after 5 seconds."
                               )

                               delay(5000)//Delay for 5 seconds
                           }
                       }
                   }
               } else {
                   Log.d(
                       "AchievementVM",
                       "Achievements already stored locally. Skipping population."
                   )
               }

               //Co routine to sync remote achievement data to local
               viewModelScope.launch {
                   var isSynced = false

                   while (!isSynced) {
                       if (application.isInternetAvailable()) {
                           try {
                               Log.d("AchievementVM", "Syncing remote to local room storage")

                               val remoteAchievements =
                                   SupabaseClient.client.postgrest["achievements"]
                                       .select {
                                           filter {
                                               eq("userID", userID)
                                           }
                                       }
                                       .decodeList<AchievementsDTO>()

                               if (remoteAchievements.isNotEmpty()) {
                                   remoteAchievements.forEach { dto ->
                                       achievementService.createOrUpdateAchievement(dto.toEntity())
                                   }

                                   Log.d(
                                       "AchievementVM",
                                       "Sync from remote to local was successful"
                                   )
                               }

                               isSynced = true
                           } catch (e: Exception) {
                               Log.e(
                                   "AchievementVM",
                                   "Error syncing remote to local: ${e.message}. Retrying after 10 seconds"
                               )

                               delay(10000)//Delay for 10 seconds
                           }
                       } else {
                           delay(5000)//Delay for 5 seconds waiting for internet connection
                       }
                   }
               }
           }
       }
    
    fun unlockAchievement(achievementTitle: String)
    {
        viewModelScope.launch {
            val userID = currentUserID

            if(userID.isBlank())
            {
                Log.w("AchievementAuthCheck", "Could not find authenticated User")

                return@launch
            }

            //Finding the achievement for the searched achievement and if the achievement has not been achieved yet
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