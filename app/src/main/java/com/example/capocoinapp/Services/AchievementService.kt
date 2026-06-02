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
    // function to get all achievements from the DAO for a specific user
    fun getAllAchievements(userID: String): Flow<List<Achievements>> {
        return achievementsDAO.getAllAchievements(userID)
    }

    suspend fun createOrUpdateAchievement(achievement: Achievements) {
        achievementsDAO.insertAchievements(achievement)
    }

}