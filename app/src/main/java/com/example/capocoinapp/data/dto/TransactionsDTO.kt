package com.example.capocoinapp.data.dto

import com.example.capocoinapp.data.entities.Transactions
import kotlinx.serialization.Serializable

@Serializable
data class TransactionsDTO(

    val transactionID: Int = 0,
    val transactionType: String,
    val transactionName: String,
    val transactionAmount: Double,
    val categoryID: Int,
    val transactionDate: String,
    val transactionTime: String,
    val dateLogged: String,
    val timeLogged: String,
    val uploadedPhotoPath: String?
)
// maps the entities from the TransactionsDTO to the Transactions
fun TransactionsDTO.toEntity(): Transactions {
    return Transactions(
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
