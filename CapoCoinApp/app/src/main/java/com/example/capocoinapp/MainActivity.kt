package com.example.capocoinapp

// import for nav host
// import for composable
// import for navController
// import for shared layout referenced from the designUI folder
// import for authentication layout referenced from the designUI folder

//

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.data.DB.AppDatabase
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.ViewModels.CategoryViewModelFactory
import com.example.capocoinapp.data.ViewModels.UserViewModel
import com.example.capocoinapp.data.ViewModels.ViewModelFactory
import com.example.capocoinapp.designUI.components.CapoCoinAuthenticationLayout
import com.example.capocoinapp.designUI.components.CapoCoinSharedLayout
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        ViewModelFactory(db.userDao())
    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        CategoryViewModelFactory(CategoryService(db.categoryDao()))
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
                    composable("Home") {
                        HomeScreen(navController)
                    }

                    // composable route to Login Screen
                    // ToDo: move to  dedicated screen file
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

                    // composable route to Categories Screen
                    composable("Categories"){
                        // Ensures the Global UI layout is applied to the Categories Screen
                        CapoCoinSharedLayout(screenTitle = "Transactions", navController = navController){ padding ->
                            Text("Categories Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to Add Categories Screen
                    composable("AddCategories"){
                        // Ensures the Global UI layout is applied to the Add Categories Screen
                        CapoCoinSharedLayout(screenTitle = "Add Categories", navController = navController){ padding ->
                            Text("Add Categories Content", modifier = Modifier.padding(padding))
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