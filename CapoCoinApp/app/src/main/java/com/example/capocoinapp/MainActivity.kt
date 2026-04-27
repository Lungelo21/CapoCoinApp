package com.example.capocoinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
// import for nav host
import androidx.navigation.compose.NavHost
// import for composable
import androidx.navigation.compose.composable
// import for navController
import androidx.navigation.compose.rememberNavController
// import for shared layout referenced from the designUI folder
import com.example.capocoinapp.designUI.components.CapoCoinSharedLayout
// import for authentication layout referenced from the designUI folder
import com.example.capocoinapp.designUI.components.CapoCoinAuthenticationLayout
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import com.example.capocoinapp.data.DB.AppDatabase
import kotlinx.coroutines.launch

import com.example.capocoinapp.data.entities.User

//
import com.example.capocoinapp.data.ViewModels.UserViewModel
import com.example.capocoinapp.data.ViewModels.ViewModelFactory

import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.ViewModels.CategoryViewModelFactory

class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        ViewModelFactory(db.userDao())
    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        CategoryViewModelFactory(db.categoryDao())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            CapoCoinAppTheme {

                // Declaring navController
                val navController = rememberNavController()

                // Nav Host wraps all composable routes
                NavHost(
                    navController = navController, startDestination = "Login"
                ){
                    // Top Navigation Bar Elements (User Profile, Settings)

                    // composable route to User Profile Screen
                    composable("UserProfile"){
                        // Ensures the Global UI layout is applied to the User Profile Screen
                        CapoCoinSharedLayout(screenTitle = "UserProfile", navController = navController){ padding ->
                            Text("User Profile Content", modifier = Modifier.padding(padding))
                        }
                    }
                    // composable route to Settings Screen
                    composable("Settings"){
                        // Ensures the Global UI layout is applied to the Settings Screen
                        CapoCoinSharedLayout(screenTitle = "Settings", navController = navController){ padding ->
                            Text("Settings Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // Bottom Navigation Bar Elements (Home, Transactions, AddTransaction, Analytics, More)

                    // composable route to Home Screen
                    composable("Home"){
                        // Ensures the Global UI layout is applied to the Home Screen
                        CapoCoinSharedLayout(screenTitle = "home", navController = navController){ padding ->
                            Text("Home Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to Transactions Screen
                    composable("Transactions"){
                        // Ensures the Global UI layout is applied to the Transactions Screen
                        CapoCoinSharedLayout(screenTitle = "Transactions", navController = navController){ padding ->
                            Text("Transactions Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to Add Transaction Screen
                    composable("AddTransaction"){
                        // Ensures the Global UI layout is applied to the Add Transaction Screen
                        CapoCoinSharedLayout(screenTitle = "Add Transaction", navController = navController){ padding ->
                            Text("Add Transaction Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to Analytics Screen
                    composable("Analytics"){
                        // Ensures the Global UI layout is applied to the Analytics Screen
                        CapoCoinSharedLayout(screenTitle = "Analytics", navController = navController){ padding ->
                            Text("Analytics Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to More Screen
                    composable("More"){
                        // Ensures the Global UI layout is applied to the More Screen
                        CapoCoinSharedLayout(screenTitle = "More", navController = navController){ padding ->
                            Text("More Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route for User budget
                    composable("UserBudget") {
                        CapoCoinSharedLayout(screenTitle = "User Budget",navController = navController) { padding ->
                            UserBudget(
                                modifier = Modifier.padding(padding),
                                categoryViewModel = categoryViewModel
                            )
                        }
                    }

                    // composable route to Login Screen
                    composable("Login"){
                        // Ensures the Authentication layout is applied to the Login Screen
                        CapoCoinAuthenticationLayout(screenTitle = "Login", navController = navController){ padding ->
                            Login(
                                modifier = Modifier.padding(padding),
                                onLoginClick = { username, password ->

                                    val validLogin = userViewModel.loginUser(
                                        username = username,
                                        password = password
                                    )

                                    if (validLogin) {
                                        navController.navigate("Home")
                                    }
                                },
                                onRegisterClick = {
                                    navController.navigate("Register")
                                }
                            )
                        }
                    }

                    // composable route to Register Screen
                    composable("Register"){
                        // Ensures the Authentication layout is applied to the Login Screen
                        CapoCoinAuthenticationLayout(
                            screenTitle = "Register",
                            navController = navController
                        ){ padding ->
                            LaunchedEffect(userViewModel.message) {
                                if (userViewModel.message == "User registered successfully") {
                                    navController.navigate("Login")
                                    userViewModel.clearMessage()
                                }
                            }
                            Register(
                                modifier = Modifier.padding(padding),
                                message = userViewModel.message,
                                onRegisterClick = { name, username, email, password, confirmPassword ->

                                    userViewModel.registerUser(
                                        name = name,
                                        username = username,
                                        email = email,
                                        password = password,
                                        confirmPassword = confirmPassword
                                    )


                                }
                            )

                        }
                    }


                }

            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CapoCoinAppTheme {
        Greeting("Android")
    }
}