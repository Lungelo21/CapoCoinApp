package com.example.capocoinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapoCoinAppTheme {

                // Declaring navController
                val navController = rememberNavController()

                // Nav Host wraps all composable routes
                NavHost(
                    navController = navController, startDestination = "home"
                ){
                    // Top Navigation Bar Elements (User Profile, Settings)

                    // composable route to User Profile Screen
                    composable("userprofile"){
                        // Ensures the Global UI layout is applied to the User Profile Screen
                        CapoCoinSharedLayout(screenTitle = "UserProfile", navController = navController){ padding ->
                            Text("User Profile Content", modifier = Modifier.padding(padding))
                        }
                    }
                    // composable route to Settings Screen
                    composable("settings"){
                        // Ensures the Global UI layout is applied to the Settings Screen
                        CapoCoinSharedLayout(screenTitle = "Settings", navController = navController){ padding ->
                            Text("Settings Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // Bottom Navigation Bar Elements (Home, Transactions, AddTransaction, Analytics, More)

                    // composable route to Home Screen
                    composable("home"){
                        // Ensures the Global UI layout is applied to the Home Screen
                        CapoCoinSharedLayout(screenTitle = "home", navController = navController){ padding ->
                            Text("Home Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to Transactions Screen
                    composable("transactions"){
                        // Ensures the Global UI layout is applied to the Transactions Screen
                        CapoCoinSharedLayout(screenTitle = "Settings", navController = navController){ padding ->
                            Text("Transactions Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to Add Transaction Screen
                    composable("addtransaction"){
                        // Ensures the Global UI layout is applied to the Add Transaction Screen
                        CapoCoinSharedLayout(screenTitle = "Add Transaction", navController = navController){ padding ->
                            Text("Add Transaction Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to Analytics Screen
                    composable("analytics"){
                        // Ensures the Global UI layout is applied to the Analytics Screen
                        CapoCoinSharedLayout(screenTitle = "Settings", navController = navController){ padding ->
                            Text("Transactions Content", modifier = Modifier.padding(padding))
                        }
                    }

                    // composable route to More Screen
                    composable("more"){
                        // Ensures the Global UI layout is applied to the More Screen
                        CapoCoinSharedLayout(screenTitle = "More", navController = navController){ padding ->
                            Text("More Content", modifier = Modifier.padding(padding))
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