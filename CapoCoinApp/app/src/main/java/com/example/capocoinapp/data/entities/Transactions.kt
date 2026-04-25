package com.example.capocoinapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(
    tableName = "Transactions",
    foreignKeys = [
        ForeignKey(
            entity = Locale.Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId_fk"],
            onDelete = ForeignKey.Companion.CASCADE // If a category is deleted, its transactions are removed
        )
    ],
    indices = [Index(value = ["categoryId_fk"])]
)
data class Transactions(
    @PrimaryKey(autoGenerate = true)
    val transactionId: Int = 0,

    val transactionName: String,
    val transactionAmount: Double,
    val categoryId_fk: Int,

    // The "Scheduled" time (user-selected)
    val transactionDate: String,
    val transactionTime: String,

    // The "System" time (auto-logged)
    val dateLogged: String,
    val timeLogged: String,

    val uploadedPhotoPath: String?
)