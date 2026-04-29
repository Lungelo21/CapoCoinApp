package com.example.capocoinapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Settings
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
fun MoreScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 4) },
            pageTitle = "More"
        ) { _ ->

            CardBox(

                cards = listOf(
                    {
                        MoreCard(
                            "Settings",
                            "Coming soon in Part 3",
                            Icons.Default.Settings,
                            { navController.navigate("Settings") })
                    },
                    {
                        MoreCard(
                            "User's Budget",
                            "View budgets for each category",
                            Icons.Default.ContentPaste,
                            { navController.navigate("UserBudget") })
                    },
                    {
                        MoreCard(
                            "Categories",
                            "View and add transaction categories",
                            Icons.Default.Category,
                            { navController.navigate("Categories") })
                    },
                    {
                        MoreCard(
                            "Import",
                            "Coming soon in Part 3",
                            Icons.Default.FileDownload
                        )
                    },
                    {
                        MoreCard(
                            "Export",
                            "Coming soon in Part 3",
                            Icons.Default.FileUpload
                        )
                    },

                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        MoreScreen(navController)
    }
}