package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CategoryCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme


@Composable
fun CategoriesScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel
) {

    val categories by categoryViewModel
        .getAllCategories()
        .collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 4) },
            pageTitle = "Categories"
        ) { _ ->

            // ToDo: replace with logic to show the actual transactions once database is set up
            categories.forEach { c ->
                CardBox(
                    cards = listOf(
                        {
                            CategoryCard(
                                c.categoryTitle,
                                categoryViewModel.getColour(c.categoryColour),
                                categoryViewModel.getIcon(c.categoryIcon)
                            )
                        }
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
    }
}