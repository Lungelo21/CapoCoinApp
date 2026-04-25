package com.example.capocoinapp.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Locale.Category

@Entity(
    tableName = "Transactions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId_fk"],
            onDelete = ForeignKey.CASCADE // If a category is deleted, its transactions are removed
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