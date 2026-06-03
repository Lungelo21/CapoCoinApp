package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.ViewModels.TransactionViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.HomeCard
import com.example.capocoinapp.designUI.components.PageSubTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.rememberCategoryUI
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import androidx.compose.runtime.LaunchedEffect
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    transactionViewModel: TransactionViewModel
) {

    val today = LocalDate.now()
    val currentMonth = YearMonth.now()

    val daysRemaining =
        currentMonth.lengthOfMonth() - today.dayOfMonth

    LaunchedEffect(Unit) {
        transactionViewModel.loadHomeBudgetFromSupabase()
    }

    val transactions by remember (transactionViewModel)
    {
        transactionViewModel.getAllTransactions()
    }.collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 1) },
            pageTitle = "Home"
        ) { _ ->

            CardBox(
                cards = listOf(
                    {
                        HomeCard(
                            totalSpent = transactionViewModel.monthlySpentFromSupabase,
                            budget = transactionViewModel.totalMaxBudgetFromSupabase,
                            daysRemaining =  daysRemaining.coerceAtLeast(0)
                        )
                    },
                    { PageSubTitleText("Recent Transactions") }
                )
            )

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
                            t.transactionType,
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

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()

    }
}