package com.example.capocoinapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.capocoinapp.data.entities.Achievements
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementsDAO {

    //Query to get all achievements
    @Query("SELECT * FROM achievements WHERE userID = :userID ORDER BY achievementID ASC")
    fun getAllAchievements(userID: String): Flow<List<Achievements>>

    //Query to get all locked achievements
    @Query("SELECT * FROM achievements WHERE userID = :userID AND isUnlocked = 0")
    fun getLockedAchievements(userID: String): Flow<List<Achievements>>

}