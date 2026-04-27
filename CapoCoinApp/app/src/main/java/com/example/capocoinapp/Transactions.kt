package com.example.capocoinapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payments
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun TransactionsScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Transactions"
        ) { _ ->

            //ToDo: add transactions filtering

            // ToDo: replace with logic to show the actual transactions once database is set up
            CardBox(
                cards = listOf(
                    {
                        CardComponent(
                            "Dinner Night",
                            "Empire Steak",
                            "200",
                            "5:00 PM",
                            Icons.Default.Fastfood,
                            "expense"
                        )
                    },
                    {
                        CardComponent(
                            "Movie",
                            "Pavillion",
                            "150",
                            "7:45 AM",
                            Icons.Default.Movie,
                            "expense"
                        )
                    },
                    {
                        CardComponent(
                            "Salary",
                            "Dunder Mifflin",
                            "30 000",
                            "9:45 AM",
                            Icons.Default.Payments,
                            "income"
                        )

                    }
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionsPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        TransactionsScreen(navController)
    }
}