package com.example.capocoinapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Locale

@Entity(
    tableName = "transactions",
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
    val transactionID: Int = 0,

    val transactionName: String,
    val transactionAmount: Double,
    val categoryID: Int, //FK -> Category

    // The "Scheduled" time (user-selected)
    val transactionDate: String,
    val transactionTime: String,

    val dateLogged: String, //Should be set to current date
    val timeLogged: String, //Should be set to current time

    val uploadedPhotoPath: String?
)