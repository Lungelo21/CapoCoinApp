package com.example.capocoinapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CategoryCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme


@Composable
fun CategoriesScreen(navController: NavController, categoryService: CategoryService) {

    val categories by categoryService
        .getAllCategories()
        .collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 4) },
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
                            //Populating the card with all data for each incremented category
                            CategoryCard(
                                cardTitle = category.categoryTitle,
                                cardColor = categoryService.getColour(category.categoryColour),
                                cardIcon = categoryService.getIcon(category.categoryIcon)
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
    }
}*/
