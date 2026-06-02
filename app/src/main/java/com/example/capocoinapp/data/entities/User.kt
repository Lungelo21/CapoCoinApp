package com.example.capocoinapp.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.capocoinapp.data.dto.UserDTO

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

// maps the entities from User to the UserDTO
fun User.toDTO(): UserDTO {
    return UserDTO(
        id = id,
        name = name,
        username = username,
        email = email
    )
}