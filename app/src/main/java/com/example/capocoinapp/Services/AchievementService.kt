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
    private val achievementsDAO: AchievementsDAO,
    private val transactionsDAO: TransactionsDAO,
    private val categoryDAO: CategoryDAO,
    private val application: Application
)
{
    // function to get all achievements from the DAO for a specific user
    fun getAllAchievements(userID: String): Flow<List<Achievements>> {
        return achievementsDAO.getAllAchievements(userID)
    }

    // function to get locked achievements from the DAO for a specific user
    fun getLockedAchievements(userID: String): Flow<List<Achievements>> {
        return achievementsDAO.getLockedAchievements(userID)
    }

    suspend fun createOrUpdateAchievement(achievement: Achievements) {
        achievementsDAO.insertAchievements(achievement)
    }

    //Function used to unlock an achievement
    suspend fun unlockAchievement(achievements: Achievements, date: String)
    {
        val unlocked = achievements.copy(isUnlocked = 1, dateUnlocked = date)

        achievementsDAO.updateAchievement(unlocked)
    }
}