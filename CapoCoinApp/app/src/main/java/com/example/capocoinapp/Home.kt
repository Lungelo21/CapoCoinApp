package com.example.capocoinapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun HomeScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    transactionViewModel: TransactionViewModel
) {

    val transactions by transactionViewModel
        .getAllTransactions()
        .collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 1) },
            pageTitle = "Home"
        ) { _ ->
            // ToDo: replace with logic to show the actual transactions once database is set up
            CardBox(
                cards = listOf(
                    { HomeCard(1300.0, 2000.0, 15) },
                    { PageSubTitleText("Recent Transactions") }
                )
            )

            transactions.forEach { t ->
                CardBox(
                    cards = listOf(
                        {
                            CardComponent(
                                t.transactionName,
                                "date",
//                                t.transactionDate,
                                t.transactionAmount.toString(),
//                                t.transactionTime,
                                "time",
                                "#000000",
                                //categoryViewModel.getColour(category.categoryColour),
                                //categoryViewModel.getIcon(category.categoryIcon),
                                Icons.Default.Help,
                                "expense",
                            )
                        }
                    )
                )
            }
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