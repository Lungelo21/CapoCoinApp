package com.example.capocoinapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.capocoinapp.data.dto.TransactionsDTO

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryID"],
            childColumns = ["categoryID"],
            onDelete = ForeignKey.CASCADE // If a category is deleted, its transactions are removed
        )
    ],
    indices = [Index(value = ["categoryID"])]
)
data class Transactions(
    @PrimaryKey(autoGenerate = true)
    val transactionID: Int = 0,

    //Adding Transaction Type -> Should be done taken as a string through dropdown
    val transactionType: String,

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

// maps the entities from Transactions to the TransactionsDTO
fun Transactions.toDTO(): TransactionsDTO {
    return TransactionsDTO(
        transactionID = transactionID,
        transactionType = transactionType,
        transactionName = transactionName,
        transactionAmount = transactionAmount,
        categoryID = categoryID,
        transactionDate = transactionDate,
        transactionTime = transactionTime,
        dateLogged = dateLogged,
        timeLogged = timeLogged,
        uploadedPhotoPath = uploadedPhotoPath
    )
}