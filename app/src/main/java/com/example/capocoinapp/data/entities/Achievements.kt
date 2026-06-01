package com.example.capocoinapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.capocoinapp.data.dto.AchievementsDTO

@Entity(tableName = "achievements")
data class Achievements(
    @PrimaryKey(autoGenerate = true)
    val achievementID: Int = 0,
    val achievementTitle: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val dateUnlocked: String?,
    val userID: String
)

// maps the entities from Achievements to the AchievementsDTO
fun Achievements.toDTO(): AchievementsDTO
{
    return AchievementsDTO(
        achievementID = achievementID,
        achievementTitle = achievementTitle,
        description = description,
        isUnlocked = isUnlocked,
        dateUnlocked = dateUnlocked,
        userID = userID
    )
}