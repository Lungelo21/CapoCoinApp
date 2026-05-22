package com.example.capocoinapp

import android.R.attr.data
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.Services.TransactionService
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.designUI.components.AnalyticsChartToggle
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CategoryAnalyticsCard
import com.example.capocoinapp.designUI.components.CategoryPieChart
import com.example.capocoinapp.designUI.components.ChartCard
import com.example.capocoinapp.designUI.components.ComposeBarChart
import com.example.capocoinapp.designUI.components.PieChartTypeToggle
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import kotlin.math.roundToInt

@Composable
fun AnalyticsScreen(
    service: TransactionService,
    categoryService: CategoryService,
    categoryViewModel: CategoryViewModel,
    navController: NavHostController
) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 3) },
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

            // Toggle state for transaction type
            var selectedType by rememberSaveable { mutableStateOf("Expense") }

            // Toggle state for chart type
            var selectedChart by rememberSaveable { mutableStateOf("Totals") }

            // Filter totals based on selected category type
            val filteredTotals = totals.filter { total ->

                val matchingCategory = categories.find {
                    it.categoryTitle == total.categoryTitle
                }

                matchingCategory?.transactionType.equals(selectedType, ignoreCase = true)
            }

            // Total amount among filtered categories for calculating percentage
            val grandTotal = filteredTotals.sumOf { it.totalAmount }

            // Map entries to pie chart data set
            val slices = filteredTotals.map { t ->

                val percentage = if (grandTotal == 0.0) {
                    0f
                } else {
                    ((t.totalAmount / grandTotal) * 100).toFloat()
                }

                val category = categories.find {
                    it.categoryTitle == t.categoryTitle
                }

                // Get category colour value from category service
                val categoryColourHex =
                    categoryService.getColour(category?.categoryColour ?: "Grey")

                PieChartData.Slice(
                    label = t.categoryTitle,
                    value = percentage,
                    color = Color(categoryColourHex.toColorInt())
                )
            }

            // Map entries to bar graph data set
            val expenseBarChartData = totals.mapNotNull { total ->

                val category = categories.find {
                    it.categoryTitle == total.categoryTitle
                }

                if (category?.transactionType?.equals("Expense", ignoreCase = true) == true) {
                    total.categoryTitle to total.totalAmount
                } else {
                    null
                }
            }


            val labels = expenseBarChartData.map { it.first }
            val data = expenseBarChartData.map { it.second }

            CardBox(
                cards = listOf() {

                    AnalyticsChartToggle(
                        selectedChart = selectedChart,
                        onChartSelected = { selectedChart = it }
                    )

                    if (slices.isNotEmpty()) {
                        ChartCard({ CategoryPieChart(slices) })
                    } else {
                        Text("No data available")
                    }

                    if (data.isNotEmpty()) {
                        ComposeBarChart(data, labels)
                    } else {
                        Text("No data available")
                    }

                    PieChartTypeToggle(
                        selectedType = selectedType,
                        onTypeSelected = { selectedType = it })

                    // Category cards
                    filteredTotals.forEach { total ->

                        // Instantiating variable to get category colour and icon
                        val category = categories.find {
                            it.categoryTitle == total.categoryTitle
                        }

                        // Convert amount to percentage
                        val percentString = if (grandTotal == 0.0) {
                            0
                        } else {
                            (((total.totalAmount ?: 0.0) / grandTotal) * 100).roundToInt()
                        }

                        //Populating the card with all data for each incremented category
                        CategoryAnalyticsCard(
                            total.categoryTitle,
                            total.totalAmount,
                            percentString,
                            categoryService.getColour(category?.categoryColour ?: "Grey"),
                            category?.categoryIcon ?: "Salary",
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