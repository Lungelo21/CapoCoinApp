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
import com.example.capocoinapp.designUI.components.HomeCard
import com.example.capocoinapp.designUI.components.PageSubTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun HomeScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Home"
        ) { _ ->
            // ToDo: replace with logic to show the actual transactions once database is set up
            CardBox(
                cards = listOf(

                    { HomeCard(1300.0, 2000.0, 15) },

                    { PageSubTitleText("Recent Transactions") },
                    {
                        CardComponent(
                            "Dinner Night",
                            "Empire Steak",
                            "- R200",
                            "5:00 PM",
                            Icons.Default.Fastfood,
                            "expense"
                        )
                    },
                    {
                        CardComponent(
                            "Movie",
                            "Pavillion",
                            "- R150",
                            "7:45 AM",
                            Icons.Default.Movie,
                            "expense"
                        )
                    },
                    {
                        CardComponent(
                            "Salary",
                            "Dunder Mifflin",
                            "+ R30 000",
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
fun HomePreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}