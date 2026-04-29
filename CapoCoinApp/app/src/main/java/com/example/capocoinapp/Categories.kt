package com.example.capocoinapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShortNavigationBarDefaults.arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.capocoinapp.ui.theme.*


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
                        Row (
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){// Add category button
                            OutlinedButton(
                                onClick = { navController.navigate("AddCategories") },
                                border = BorderStroke(3.dp, Accent),

                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Accent
                                )
                            ) {
                                Text("Add Category")
                            }

                            Spacer(modifier= Modifier.width(40.dp) )

                            // Category total button
                            OutlinedButton(
                                onClick = { navController.navigate("CategoryTotals")},
                                border = BorderStroke(3.dp, Accent),

                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Accent
                                )
                            ){
                                Text("Category total")
                            }

                        }

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
