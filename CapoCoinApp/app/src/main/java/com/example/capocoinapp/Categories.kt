package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CategoryCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme


@Composable
fun CategoriesScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Categories"
        ) { _ ->

            // ToDo: replace with logic to show the actual transactions once database is set up
            CardBox(
                cards = listOf(
                    {
                        CategoryCard(
                            "Food",
                            "Grey",
                            "Food"
                        )
                    },
                    {
                        CategoryCard(
                            "Gym",
                            "Teal",
                            "Gym"
                        )
                    },
                    {
                        CategoryCard(
                            "Savings",
                            "#2E7D32",
                            "Savings"
                        )
                    },
                    {
                        CategoryCard(
                            "Savings",
                            "#2E7D32",
                            "Savings",
                        )
                    },
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        CategoriesScreen(navController)
    }
}