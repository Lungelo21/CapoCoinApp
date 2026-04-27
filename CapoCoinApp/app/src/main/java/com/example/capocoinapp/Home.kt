package com.example.capocoinapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payments
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.PageTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun HomeScreen() {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar() },
            bottomBar = { BottomNavBar() },
            pageTitle = "Home"
        ) { _ ->

            //ToDo: add progress bar card

            PageTitleText("Recent Transactions")

            // ToDo: replace with logic to show the actual transactions once database is set up
            CardBox(
                cards = listOf(
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
        HomeScreen()
    }
}