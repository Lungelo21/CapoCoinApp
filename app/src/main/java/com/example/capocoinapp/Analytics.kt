package com.example.capocoinapp

import android.R.attr.category
import android.R.attr.onClick
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.Services.TransactionService
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.BudgetCard
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.CategoryCard
import com.example.capocoinapp.designUI.components.PageTitleText
import com.example.capocoinapp.designUI.components.PieChartView
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.rememberCategoryUI
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.github.mikephil.charting.data.PieEntry

@Composable
fun AnalyticsScreen(service: TransactionService,
                    categoryService: CategoryService,
                    categoryViewModel: CategoryViewModel,
                    navController: NavHostController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController,3) },
            pageTitle = "Analytics"
        ) { _ ->

            //Instantiating a variable to hold the users current
            val context = LocalContext.current

            //Instantiating variables for the user selected start and end dates for filtering
            var startDate by rememberSaveable { mutableStateOf("") }
            var endDate by rememberSaveable { mutableStateOf("") }

            // Instantiating variables to hold category data
            val totals by service.getCategoryTotals(startDate, endDate)
                .collectAsState(initial = emptyList())

            val categories by categoryViewModel
                .getAllCategories()
                .collectAsState(initial = emptyList())

            // Total amount among all categories for calculating percentage
            val grandTotal = totals.sumOf { it.totalAmount }

            // Convert data from list to dataset that can be used in the pie chart
            val sampleData = totals.associate { item ->

                // Calculate percentage
                val percentage = if (grandTotal == 0.0) {
                    0f
                } else {
                    ((item.totalAmount / grandTotal) * 100).toFloat()
                }

                item.categoryTitle to percentage
            }

            // Render pie chart
            PieChartView(data = sampleData)

            // Render categories with the totals and percentages
            CardBox(
                cards = listOf(){
                    totals.forEach { t ->

                        // Instantiating variable to get category colour and icon
                        val category = categories.find { it.categoryTitle == t.categoryTitle }

                        //Populating the card with all data for each incremented category
                        BudgetCard(
                            cardTitle = t.categoryTitle,
                            cardMin = t.totalAmount,
                            cardMax = t.totalAmount,
                            cardColor = categoryService.getColour(category?.categoryColour ?: "Grey"),
                            cardIcon = category?.categoryIcon ?: "Salary",
                            onClick = {}
                        )
                    }
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnalyticsPreview() {
    CapoCoinAppTheme {
    }
}