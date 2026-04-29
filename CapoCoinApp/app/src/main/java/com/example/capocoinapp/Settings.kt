package com.example.capocoinapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.MoreCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

//ToDo: this screen will need to be updated as more screens are added
@Composable
fun SettingsScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController,4) },
            pageTitle = "Settings"
        ) { _ ->

            CardBox(

                cards = listOf(
                    { MoreCard(
                        "Notifications",
                        "Coming soon in Part 3",
                        Icons.Default.Notifications)},
                    { MoreCard(
                        "App Lock",
                        "Coming soo in Part 3",
                        Icons.Default.Lock,)},
                    { MoreCard(
                        "Default Page",
                        "Coming soon in Part 3",
                        Icons.Default.Home)},
                    { MoreCard(
                        "Default Currency",
                        "Coming soon in Part 3",
                        Icons.Default.CurrencyExchange)},

                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        SettingsScreen(navController)
    }
}