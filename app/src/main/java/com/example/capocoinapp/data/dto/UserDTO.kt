package com.example.capocoinapp.data.dto

import com.example.capocoinapp.data.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: String,
    val name: String,
    val username: String,
    val email: String
)

// maps the entities from the UserDTO to the User
fun UserDTO.toEntity(): User{
return User(
    id = id,
    name = name,
    username = username,
    email = email
    )
}