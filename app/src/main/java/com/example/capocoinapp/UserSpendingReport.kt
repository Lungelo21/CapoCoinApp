package com.example.capocoinapp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.capocoinapp.Services.CategoryService
import com.example.capocoinapp.Services.TransactionService
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.designUI.components.AnalyticsChartToggle
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.BudgetAnalyticsCard
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CategoryAnalyticsCard
import com.example.capocoinapp.designUI.components.CategoryPieChart
import com.example.capocoinapp.designUI.components.ChartCard
import com.example.capocoinapp.designUI.components.ComposeBarChart
import com.example.capocoinapp.designUI.components.PieChartTypeToggle
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.rememberCategoryUI
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.TextWhite
import kotlin.math.roundToInt

@Composable
fun UserSpendingReportScreen(
    service: TransactionService,
    categoryService: CategoryService,
    categoryViewModel: CategoryViewModel,
    navController: NavHostController
) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 4) },
            pageTitle = "User Spending Report"
        ) { _ ->

            //Instantiating a variable to hold the users current
            val context = LocalContext.current

            //Instantiating variables for the user selected start and end dates for filtering
            var startDate by rememberSaveable { mutableStateOf("") }
            var endDate by rememberSaveable { mutableStateOf("") }

            //Instantiating a variable to hold the user's current scroll state
            val scrollState = androidx.compose.foundation.rememberScrollState()

            //Instantiating a Date Picker
            val showDatePicker = { isStartDate: Boolean ->
                //Instantiating a Calendar
                val calendar = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        // Format as YYYY-MM-DD for the Service/DAO
                        val formatted = String.format("%04d-%02d-%02d", year, month + 1, day)

                        if (isStartDate) {
                            startDate = formatted
                        } else {
                            endDate = formatted
                        }
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()

                /*
                 * Author: Kotlin Programming Language
                 * Link: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-date-picker-dialog.html
                 * DateAccessed: 28/04/2026
                 * */

                /*
                 * Author: GeeksforGeeks
                 * Link: https://www.geeksforgeeks.org/android/datepickerdialog-in-android/
                 * DateAccessed: 28/04/2026
                 * */
            }

            // Instantiating variables to hold category data
            val totals by service.getCategoryTotals(startDate, endDate)
                .collectAsState(initial = emptyList())

            val categories by categoryViewModel
                .getAllCategories()
                .collectAsState(initial = emptyList())

            // Toggle state for transaction type
            var selectedType by rememberSaveable { mutableStateOf("Expense") }

            // Filter totals based on selected category type
            val filteredTotals = totals.filter { total ->

                val matchingCategory = categories.find {
                    it.categoryTitle == total.categoryTitle
                }

                matchingCategory?.transactionType.equals(selectedType, ignoreCase = true)
            }

            // Instantiate data set to use with bar graph
            data class BudgetChartItem(
                val label: String,
                val actual: Double,
                val min: Double,
                val max: Double
            )

            // Map entries to bar graph data set
            val expenseChartData = totals.mapNotNull { total ->

                val category = categories.find {
                    it.categoryTitle == total.categoryTitle
                } ?: return@mapNotNull null

                val isExpense = category.transactionType
                    .equals("Expense", ignoreCase = true)

                if (!isExpense) return@mapNotNull null

                BudgetChartItem(
                    label = total.categoryTitle,
                    actual = total.totalAmount,
                    min = category.minBudget,
                    max = category.maxBudget
                )
            }

            // Extract lists from data set
            val labels = expenseChartData.map { it.label }
            val data = expenseChartData.map { it.actual }
            val minBudget = expenseChartData.map { it.min }
            val maxBudget = expenseChartData.map { it.max }

            CardBox(
                cards = listOf() {

                    // Render bar graph
                    if (data.isNotEmpty()) {
                        ChartCard({ ComposeBarChart(data, minBudget, maxBudget, labels) })
                        selectedType = "Expense"
                    } else {
                        Text("No data available")
                    }

                    //Row for Date Selection
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        // Start Date Button
                        OutlinedButton(
                            onClick = { showDatePicker(true) },
                            modifier = Modifier.weight(1f)
                        )
                        {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            )
                            {
                                //Added an Icon to the Filter button for easier readability and usability
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp),
                                    tint = TextWhite
                                )

                                //Setting the text for the End Date Filter button and accounts when an end date is selected
                                Text(
                                    text = if (startDate.isEmpty()) "Start Date" else "From: $startDate",
                                    color = TextWhite,
                                )
                            }
                        }
                        // End Date Button
                        OutlinedButton(
                            onClick = { showDatePicker(false) },
                            modifier = Modifier.weight(1f)
                        )
                        {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            )
                            {
                                //Added an Icon to the Filter button for easier readability and usability
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp),
                                    tint = TextWhite
                                )

                                //Setting the text for the End Date Filter button and accounts when an end date is selected
                                Text(
                                    text = if (endDate.isEmpty()) "End Date" else "To: $endDate",
                                    color = TextWhite
                                )
                            }
                        }
                    }

                    //Check to ensure Clear Filters button wont appear if no filter has been made
                    if (startDate.isNotEmpty() || endDate.isNotEmpty()) {
                        //Instantiating the button for the Clear Filter with empty values (no filter - all days)
                        OutlinedButton(
                            onClick = {
                                startDate = ""
                                endDate = ""
                            },
                            //Making the button take up the fill width of the screen
                            modifier = Modifier.fillMaxWidth()
                        )
                        //Setting button's text
                        {
                            Text("Clear All filters", color = TextWhite)
                        }
                    }

                    // Render bar graph data
                    if (data.isNotEmpty()) {
                        // Category cards
                        filteredTotals.forEach { total ->

                            // Instantiating variable to get category colour and icon
                            val category = categories.find {
                                it.categoryTitle == total.categoryTitle
                            }

                            val (CategoryColor, CategoryIcon) =
                                rememberCategoryUI(category?.categoryID ?: 0, categoryViewModel)

                            // Render cards
                            BudgetAnalyticsCard(
                                cardTitle = total.categoryTitle,
                                cardMin = category?.minBudget,
                                cardAmount = total.totalAmount,
                                cardMax = category?.maxBudget,
                                categoryColor = CategoryColor,
                                categoryIcon = CategoryIcon,
                            )
                        }
                        selectedType = "Expense"
                    } else {
                        Text("No data available")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserSpendingReportPreview() {
    CapoCoinAppTheme {
    }
}