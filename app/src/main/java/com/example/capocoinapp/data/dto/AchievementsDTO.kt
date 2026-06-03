package com.example.capocoinapp.data.dto

import com.example.capocoinapp.data.entities.Achievements
import com.example.capocoinapp.data.entities.Transactions
import kotlinx.serialization.Serializable

@Serializable
data class AchievementsDTO(
    val achievementID: Int = 0,
    val achievementTitle: String,
    val description: String,
    val isUnlocked: Int = 0,
    val dateUnlocked: String?,
    val userID: String
)

// maps the entities from the AchievementsDTO to the Achievements
fun AchievementsDTO.toEntity(): Achievements {
    return Achievements(
        achievementID = achievementID,
        achievementTitle = achievementTitle,
        description = description,
        isUnlocked = isUnlocked,
        dateUnlocked = dateUnlocked,
        userID = userID
    )
}