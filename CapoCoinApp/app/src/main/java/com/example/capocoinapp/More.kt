package com.example.capocoinapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

//ToDo: this screen will need to be updated as more screens are added
@Composable
fun MoreScreen() {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar() },
            bottomBar = { BottomNavBar() },
            pageTitle = "More"
        ) { _ ->

            CardBox(
                cards = listOf(
                    {
                        CardComponent(
                            "Settings",
                            "Change app settings",
                            null,
                            null,
                            Icons.Default.Settings,
                            null
                        )
                    },
                    {
                        CardComponent(
                            "User's budget",
                            "Change app settings",
                            null,
                            null,
                            Icons.Default.PieChart,
                            null
                        )
                    },
                    {
                        CardComponent(
                            "User Spending Report",
                            "Change app settings",
                            null,
                            null,
                            Icons.Default.ContentPaste,
                            null
                        )
                    },
                    {
                        CardComponent(
                            "User Goals",
                            "Change app settings",
                            null,
                            null,
                            Icons.Default.Flag,
                            null
                        )
                    }

                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CapoCoinAppTheme {
        MoreScreen()
    }
}