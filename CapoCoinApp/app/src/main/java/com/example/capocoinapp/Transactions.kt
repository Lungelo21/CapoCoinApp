package com.example.capocoinapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.DatePickerCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType

@Composable
fun TransactionsScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController,2) },
            pageTitle = "Transactions"
        ) { _ ->

            //ToDo: add transactions filtering

            // Values used for selecting the date range
            var selectedDateFrom by remember { mutableStateOf("") }
            var selectedDateTo by remember { mutableStateOf("") }

            // ToDo: replace with logic to show the actual transactions once database is set up
            CardBox(
                cards = listOf(

                    { Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Filter Transactions",
                        style = CapoType.cardTitle,
                        textAlign = TextAlign.Center
                    ) },

                    {DatePickerCard(
                        selectedTransactionDate = selectedDateFrom,
                        onTransactionDateSelected = { selectedDateFrom = it},
                        placeholderText = "Select date to search from",
                        enabled = true)},
                    {DatePickerCard(
                        selectedTransactionDate = selectedDateTo,
                        onTransactionDateSelected = { selectedDateTo = it},
                        placeholderText = "Select date to search to",
                        enabled = true)},

                    //ToDo: add category filter

                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionsPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        TransactionsScreen(navController)
    }
}