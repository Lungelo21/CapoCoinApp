package com.example.capocoinapp.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["username"], unique = true)])
data class User(

    @PrimaryKey
    val id: String,
    val name : String,
    val username : String,
    val email : String,

)