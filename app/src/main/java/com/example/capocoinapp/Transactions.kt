package com.example.capocoinapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.ViewModels.TransactionViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.DatePickerCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.rememberCategoryUI
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.TextWhite

@Composable
fun TransactionsScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    transactionViewModel: TransactionViewModel
) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController,2) },
            pageTitle = "Transactions"
        ) { _ ->

            //ToDo: add transactions filtering

            // Values used for selecting the date range
            var startDate by remember { mutableStateOf("") }
            var endDate by remember { mutableStateOf("") }

            val transactions by transactionViewModel
                .getAllTransactions()
                .collectAsState(initial = emptyList())

            val filteredTransactions by transactionViewModel
                .getFilterTransactions(startDate, endDate)
                .collectAsState(initial = emptyList())

            //Check to ensure Clear Filters button wont appear if no filter has been made
            if(startDate.isNotEmpty() || endDate.isNotEmpty())
            {
                //Instantiating the button for the Clear Filter with empty values (no filter - all days)
                OutlinedButton(onClick = {
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

            CardBox(
                cards = listOf(
                    { Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Filter Transactions",
                        style = CapoType.cardTitle,
                        textAlign = TextAlign.Center
                    ) },

                    {DatePickerCard(
                        selectedTransactionDate = startDate,
                        onTransactionDateSelected = { startDate = it},
                        placeholderText = "Select date to search from",
                        enabled = true)},
                    {DatePickerCard(
                        selectedTransactionDate = endDate,
                        onTransactionDateSelected = { endDate = it},
                        placeholderText = "Select date to search to",
                        enabled = true)},
                )
            )

            if(startDate.isNotEmpty() || endDate.isNotEmpty()) {
                filteredTransactions.forEach { t ->
                    CardBox(
                        cards = listOf(
                            {
                                val (categoryColor, CategoryIcon) = rememberCategoryUI(t.categoryID, categoryViewModel)
                                CardComponent(
                                    t.transactionName,
                                    t.transactionDate,
                                    t.transactionAmount.toString(),
                                    t.transactionTime,
                                    categoryColor,
                                    CategoryIcon,
                                    "expense",
                                    {navController.navigate("TransactionDetails/${t.transactionID}")}
                                )
                            }
                        )
                    )
                }
            }else{
                CardBox(
                    cards = transactions.map { t ->
                        {
                            val (categoryColor, CategoryIcon) =
                                rememberCategoryUI(t.categoryID, categoryViewModel)

                            CardComponent(
                                t.transactionName,
                                t.transactionDate,
                                t.transactionAmount.toString(),
                                t.transactionTime,
                                categoryColor,
                                CategoryIcon,
                                "expense",
                                {
                                    navController.navigate(
                                        "TransactionDetails/${t.transactionID}"
                                    )
                                }
                            )
                        }
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionsPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
    }
}