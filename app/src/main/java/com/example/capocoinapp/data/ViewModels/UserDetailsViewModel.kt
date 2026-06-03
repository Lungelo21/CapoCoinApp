package com.example.capocoinapp.data.ViewModels


import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.capocoinapp.UserDetails
import com.example.capocoinapp.data.dao.UserDAO
import com.example.capocoinapp.data.entities.User
import com.example.capocoinapp.Supabase.SupabaseClient
import com.example.capocoinapp.Utils.isInternetAvailable
import com.example.capocoinapp.data.dto.UserDTO
import com.example.capocoinapp.data.dto.toEntity
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val dao: UserDAO,
    private val userID: String,
    private val application: Application
) : ViewModel() {
    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    var currentUserID: String = ""
        private set

    init {
        viewModelScope.launch {

            val currentUser = dao.getAllUsers().first().firstOrNull()
            currentUserID = currentUser?.id ?: ""

            if (currentUserID.isBlank()) {
                Log.w("UserDetailsVM", "No user found in RoomDB")
            } else {
                Log.d("UserDetailsVM", "Current user ID: $currentUserID")
            }

            // Loads user details from RoomDB first
            val localUser = dao.getUser(userID).first()
            if (localUser != null) {
                _userDetails.value = localUser
                Log.d("UserDetailsVM", "Loaded user from RoomDB")
            }

            // Sync from Supabase if online
            if (application.isInternetAvailable()) {
                try {
                    val onlineUser = SupabaseClient.client.postgrest
                        .from("users")
                        .select {
                            filter { eq("id", userID) }
                        }
                        .decodeSingle<UserDTO>()

                    dao.insertUser(onlineUser.toEntity())
                    _userDetails.value = onlineUser.toEntity()

                    Log.d("UserDetailsVM", "Synced user from Supabase to Room")

                } catch (e: Exception) {

                    Log.e("UserDetailsVM", "Remote user fetch was unsuccessful: ${e.message}")
                    _error.value = e.message
                }
            } else {
                Log.d("UserDetailsVM", "Currently offline - showing only RoomDB information")
            }

        }
    }

class UserDetailsViewModelFactory(private val dao: UserDAO, private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            val application =
                checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            @Suppress("UNCHECKED_CAST")
            return UserDetailsViewModel(dao, userId, application) as T

        }
        throw IllegalArgumentException("Error Occurred")
    }
}
}