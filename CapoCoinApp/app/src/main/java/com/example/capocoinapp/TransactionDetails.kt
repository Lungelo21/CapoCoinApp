package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun TransactionsDetailsScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Transactions"
        ) { _ ->

            // ToDo: replace with logic to show the actual transactions once database is set up
            CardBox(
                cards = listOf(

                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailsPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        TransactionsDetailsScreen(navController)
    }
}