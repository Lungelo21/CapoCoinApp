package com.example.capocoinapp.data.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.capocoinapp.UserDetails
import com.example.capocoinapp.data.dao.UserDAO
import com.example.capocoinapp.data.entities.User
import com.example.capocoinapp.Supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val dao: UserDAO) : ViewModel()
{
    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init{
        viewModelScope.launch {
            try{
                val result = SupabaseClient.client.postgrest
                    .from("users")
                    .select {
                        filter { eq("id", userID) }
                    }
                    .decodeSingle<User>()

                _userDetails.value = result
            }
            catch (e: Exception){
                _error.value = e.message
            }
        }
    }
}

class UserDetailsViewModelFactory(private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailsViewModel(userId) as T
        }
        throw IllegalArgumentException("Error Occurred")
    }
}