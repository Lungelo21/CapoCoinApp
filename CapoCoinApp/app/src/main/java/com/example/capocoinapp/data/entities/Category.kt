package com.example.capocoinapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryID: Int = 0,

    //Adding a val to hold transaction type for category
    val transactionType: String,

    //Store the user selected name for the category
    val categoryTitle: String,

    // Store the color as a String Hex (e.g., "#D4AF37")
    val categoryColour: String,

    // Store the name of the icon naming convention (e.g., "shopping_cart"")
    val categoryIcon: String
)
