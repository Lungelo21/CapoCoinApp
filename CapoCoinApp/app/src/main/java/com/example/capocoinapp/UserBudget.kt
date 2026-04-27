package com.example.capocoinapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.entities.Category
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.BudgetCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import androidx.compose.runtime.*

import androidx.compose.ui.unit.dp


@Composable
fun UserBudget(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel,
    message: String = "",
    onAddCategoryClick: () -> Unit = {}
) {
    val categories by categoryViewModel
        .getAllCategories()
        .collectAsState(initial = emptyList())

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var minBudgetInput by remember { mutableStateOf("") }
    var maxBudgetInput by remember { mutableStateOf("") }

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar() },
            bottomBar = { BottomNavBar() },
            pageTitle = "User Budget"
        ) { _ ->

            Column(
                modifier = modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                /*
                * Author: Donn Felker
                * Link: https://www.youtube.com/watch?v=VE7mCMK5djM
                * Date Accessed: 27/04/2026
                */

                categories.forEach { category ->
                    BudgetCard(
                        cardTitle = category.categoryTitle,
                        cardMin = category.minBudget,
                        cardMax = category.maxBudget,
                        cardIcon = category.categoryIcon,
                        onClick = {
                            selectedCategory = category
                            minBudgetInput = category.minBudget.toString()
                            maxBudgetInput = category.maxBudget.toString()
                        }
                    )
                }

                /*
                * Author: Kotlin Programming Language
                * Link: https://kotlinlang.org/docs/scope-functions.html#let
                * Date Accessed: 27/04/2026
                */

                selectedCategory?.let { category ->
                    Text("Edit ${category.categoryTitle}")

                    OutlinedTextField(
                        value = minBudgetInput,
                        onValueChange = { minBudgetInput = it },
                        label = { Text("Minimum Budget") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = maxBudgetInput,
                        onValueChange = { maxBudgetInput = it },
                        label = { Text("Maximum Budget") },
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


