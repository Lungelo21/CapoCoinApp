package com.example.capocoinapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.entities.Category
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.BudgetCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.SubTextWhite
import com.example.capocoinapp.ui.theme.TextWhite


@Composable
fun UserBudgetScreen(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel,
    categoryService: CategoryService,
    message: String = "",
    onAddCategoryClick: () -> Unit = {},
    navController: NavController
) {
    val categories by categoryViewModel
        .getAllCategories()
        .collectAsState(initial = emptyList())

    val capoColorTextField = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = TextWhite,
        unfocusedBorderColor = SubTextWhite,
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite,
        cursorColor = Accent,
        focusedLabelColor = Accent,
        unfocusedLabelColor = SubTextWhite
    )

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var minBudgetInput by remember { mutableStateOf("") }
    var maxBudgetInput by remember { mutableStateOf("") }

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "User Budget"
        ) { _ ->

            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("AddCategories")},
                    border = BorderStroke(3.dp, Accent),

                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Accent
                    )
                ){
                    Text("Add Category")
                }

                /*
                * Author: Donn Felker
                * Link: https://www.youtube.com/watch?v=VE7mCMK5djM
                * Date Accessed: 27/04/2026
                */

                categories.forEach { category ->
                    if(category.transactionType == "Expense")
                    {
                        BudgetCard(
                            cardTitle = category.categoryTitle,
                            cardMin = category.minBudget,
                            cardMax = category.maxBudget,//Changed from hard coded colour to take from DB
                            cardColor = categoryService.getColour(category.categoryColour),//using service called method
                            cardIcon = category.categoryIcon,
                            onClick = {
                                selectedCategory = category
                                minBudgetInput = category.minBudget.toString()
                                maxBudgetInput = category.maxBudget.toString()
                            }
                        )
                    }
                }

                /*
                * Author: Kotlin Programming Language
                * Link: https://kotlinlang.org/docs/scope-functions.html#let
                * Date Accessed: 27/04/2026
                */

                selectedCategory?.let { category ->
                    Text("Edit ${category.categoryTitle}", color = SubTextWhite)

                    OutlinedTextField(
                        value = minBudgetInput,
                        onValueChange = { minBudgetInput = it },
                        label = { Text("Minimum Budget") },
                        colors = capoColorTextField,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = maxBudgetInput,
                        onValueChange = { maxBudgetInput = it },
                        label = { Text("Maximum Budget") },
                        colors = capoColorTextField,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            val min = minBudgetInput.toDoubleOrNull() ?: 0.0
                            val max = maxBudgetInput.toDoubleOrNull() ?: 0.0

                            categoryViewModel.updateCategoryBudget(
                                category = category,
                                minBudget = min,
                                maxBudget = max
                            )
                            selectedCategory = null
                        }
                    ) {
                        Text("Save Budget")
                    }
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun BudgetCardPreviewList() {
    CapoCoinAppTheme {
        Column {
            BudgetCard(
                cardTitle = "Food",
                cardMin = 100.0,
                cardMax = 500.0,
                cardColor = "Teal",
                cardIcon = "Food"
            )

            BudgetCard(
                cardTitle = "Transport",
                cardMin = 200.0,
                cardMax = 800.0,
                cardColor = "Teal",
                cardIcon = "Transport"
            )
        }
    }
}
