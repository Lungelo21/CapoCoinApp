package com.example.capocoinapp.data.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capocoinapp.data.dao.UserDAO
import com.example.capocoinapp.data.entities.User
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import com.example.capocoinapp.Supabase.SupabaseClient
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest


class UserViewModel (
    private val dao: UserDAO?,

): ViewModel() {

    //validation
    var message by mutableStateOf("")
        private set

    //track user login status
    var isLoggedIn by mutableStateOf(false)
        private set

    fun clearMessage() {
        message = ""
    }

    fun clearLoginState() {
        isLoggedIn = false
    }

    fun getAllUsers(): Flow<List<User>> {
        return dao?.getAllUsers() ?: emptyFlow()
    }

    fun registerUser(
        name: String,
        username: String,
        password: String,
        confirmPassword: String,
        email: String
    ) {

        val validatePassword = Regex("^(?=.*[A-Z])(?=.*\\d).{6,}$")

        viewModelScope.launch {
            //States a coroutine tied the lifecycle of this vm
            message = when {
                name.isBlank() ||
                        username.isBlank() ||
                        email.isBlank() ||
                        password.isBlank() ||
                        confirmPassword.isBlank() -> {
                    "Please complete all fields"
                }

                !(email.contains(".") && email.contains("@")) -> {
                    "Email not valid email"
                }

                !validatePassword.matches(password) -> {
                    "Password needs at least one capital,one special character, one digit and at least 6 character length"
                }

                password != confirmPassword -> {
                    "Passwords do not match"
                }

                else -> {

                    val cleanUsername = username.trim()

                    val existingUser = dao?.getUserByUsername(cleanUsername)

                    if (existingUser != null) {
                        "Username already exists"
                    } else {
                        try {
                            // 1. Register with Supabase Auth
                            val result = SupabaseClient.client.auth.signUpWith(Email) {
                                this.email = email
                                this.password = password
                            }

                            val userId = result?.id ?: throw Exception("User ID is null")

                            // 2. Save user in Supabase DB table
                            val userMap = mapOf(
                                "id" to userId,
                                "name" to name.trim(),
                                "email" to email,
                                "username" to username
                            )

                            SupabaseClient.client.postgrest
                                .from("users")
                                .insert(userMap)


                            val user = User(
                                id = userId,
                                name = name.trim(),
                                username = username,
                                email = email.trim()
                            )

                            dao?.insertUser(user)

                            "User registered successfully"

                        } catch (e: Exception) {
                            "Registration failed: ${e.message}"
                        }
                    }
                }
            }
        }

    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            message = when {
                email.isBlank() || password.isBlank() -> {
                    "Please enter email and password"
                }

                else -> {
                    try {
                        SupabaseClient.client.auth.signInWith(Email) {
                            this.email = email.trim()
                            this.password = password
                        }

                        isLoggedIn = true
                        "Login successful"

                    } catch (e: Exception) {
                        val cachedUser = dao?.getUserByEmail(email.trim())

                        if (cachedUser != null) {
                            isLoggedIn = true
                            "Offline login successful"
                        } else {
                            isLoggedIn = false
                            "Login failed. Connect to the internet first."
                        }



                    }
                }
            }
        }

    }

    class ViewModelFactory(
        private val dao: UserDAO
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(dao) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
