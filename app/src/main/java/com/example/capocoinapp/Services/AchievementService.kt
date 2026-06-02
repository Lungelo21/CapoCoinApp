package com.example.capocoinapp.Services

import android.app.Application
import com.example.capocoinapp.data.dao.AchievementsDAO
import com.example.capocoinapp.data.dao.CategoryDAO
import com.example.capocoinapp.data.dao.TransactionsDAO
import com.example.capocoinapp.data.entities.Achievements
import com.example.capocoinapp.data.entities.Transactions
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Locale

class AchievementService(
    private val achievementsDAO: AchievementsDAO
)
{
    //Storing all User Achievements in list of pairs for achievement title and description
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
        Pair("Big Spender", "Log a single transaction worth over R500"),
        Pair("The Century Club", "Log 100 total transactions")

    )

    // function to get all achievements from the DAO for a specific user
    fun getAllAchievements(userID: String): Flow<List<Achievements>> {
        return achievementsDAO.getAllAchievements(userID)
    }

    suspend fun createOrUpdateAchievement(achievement: Achievements) {
        achievementsDAO.insertAchievements(achievement)
    }

    //Function to populate all user achievements
    suspend fun populateUserAchievements(currentUserID: String)
    {
        userAchievements.forEach { achievements ->
            createOrUpdateAchievement(
                Achievements(
                    achievementTitle = achievements.first,
                    description = achievements.second,
                    isUnlocked = 0,
                    dateUnlocked = null,
                    userID = currentUserID
                )
            )
        }
    }

}