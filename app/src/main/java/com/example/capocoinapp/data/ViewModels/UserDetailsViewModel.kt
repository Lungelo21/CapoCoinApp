package com.example.capocoinapp.data.ViewModels

<<<<<<< Updated upstream
=======
import android.app.Application
import android.util.Log
>>>>>>> Stashed changes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.capocoinapp.UserDetails
import com.example.capocoinapp.data.dao.UserDAO
import com.example.capocoinapp.data.entities.User
import com.example.capocoinapp.Supabase.SupabaseClient
<<<<<<< Updated upstream
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val dao: UserDAO) : ViewModel()
{
=======
import com.example.capocoinapp.Utils.isInternetAvailable
import com.example.capocoinapp.data.dto.UserDTO
import com.example.capocoinapp.data.dto.toEntity
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val dao: UserDAO,
    private val userID: String,
    private val application: Application
) : ViewModel() {
>>>>>>> Stashed changes
    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init{
        viewModelScope.launch {
<<<<<<< Updated upstream
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
=======

            val localUser = dao.getUser(userID).first()

            if(localUser != null){
                _userDetails.value = localUser
                Log.d("UserDetailsVM", "Loaded user from roomdb")
            }
            if(application.isInternetAvailable()){

                try{
                    val onlineUser = SupabaseClient.client.postgrest
                        .from("users")
                        .select {
                            filter { eq("id", userID) }
                        }
                        .decodeSingle<UserDTO>()
                    // Updates Room with latest from Supabase
                    dao.insertUser(onlineUser.toEntity())

                    // updates the UI with the latest user information
                    _userDetails.value = onlineUser.toEntity()
                    Log.d("UserDetailsVM", "Synced user Supabase to Room")
                }
                catch (e: Exception){
                    Log.e("UserDetailsVM", "Remote user fetch was unsuccessful ${e.message}")
                    _error.value = e.message
                }
            }
            else{
                Log.d("UserDetailsVM", "Currently Offline - showing only the roomdb information")
            }

>>>>>>> Stashed changes
        }
    }
}

<<<<<<< Updated upstream
class UserDetailsViewModelFactory(private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailsViewModel(userId) as T
=======
class UserDetailsViewModelFactory(private val dao: UserDAO, private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            @Suppress("UNCHECKED_CAST")
            return UserDetailsViewModel(dao, userId, application) as T
>>>>>>> Stashed changes
        }
        throw IllegalArgumentException("Error Occurred")
    }
}