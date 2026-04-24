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
// import for navController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Declaring navController
            val navController = rememberNavController()

            // Top Navigation Bar Elements (User Profile, Settings)

            composable("userprofile"){
                // Ensures the Global UI layout is applied to the User Profile Screen
                CapoCoinSharedLayout(screenTitle = "UserProfile", navController = navController){ padding ->
                    UserProfileScreen(padding)
                }
            }

            composable("settings"){
                // Ensures the Global UI layout is applied to the Settings Screen
                CapoCoinSharedLayout(screenTitle = "Settings", navController = navController){ padding ->
                    SettingsScreen(padding)
                }
            }

            // Bottom Navigation Bar Elements (Home, Transactions, Add Transaction, Analytics, More)

            composable("home"){
                // Ensures the Global UI layout is applied to the Home Screen
                CapoCoinSharedLayout(screenTitle = "Home", navController = navController){ padding ->
                    HomeScreen(padding)
                }
            }

            composable("transactions"){
                // Ensures the Global UI layout is applied to the Transactions Screen
                CapoCoinSharedLayout(screenTitle = "Transactions", navController = navController){ padding ->
                    TransactionsScreen(padding)
                }
            }

            composable("addtransaction"){
                // Ensures the Global UI layout is applied to the Add Transaction Screen
                CapoCoinSharedLayout(screenTitle = "AddTransaction", navController = navController){ padding ->
                    AddTransactionScreen(padding)
                }
            }

            composable("analytics"){
                // Ensures the Global UI layout is applied to the Analytics Screen
                CapoCoinSharedLayout(screenTitle = "Analytics", navController = navController){ padding ->
                    AnalyticsScreen(padding)
                }
            }

            composable("more"){
                // Ensures the Global UI layout is applied to the More Screen
                CapoCoinSharedLayout(screenTitle = "More", navController = navController){ padding ->
                    MoreScreen(padding)
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