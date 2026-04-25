package com.example.capocoinapp.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["username"], unique = true)])
data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name : String,
    val username : String,
    val email : String,
    val password : String,
)