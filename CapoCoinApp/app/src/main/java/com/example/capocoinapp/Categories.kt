package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CategoryCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun CategoriesScreen(navController: NavController, categoryService: CategoryService) {

    val categories by categoryService
        .getAllCategories()
        .collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Categories"
        ) { _ ->

            CardBox(
                cards = listOf(
                    {
                        categories.forEach { category ->
                            CategoryCard(
                                cardTitle = category.categoryTitle,
                                cardColor = categoryService.getColour(category.categoryColour),
                                cardIcon = category.categoryIcon
                            )
                        }
                    }
                )
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun CategoriesPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        CategoriesScreen(navController)
    }
}*/