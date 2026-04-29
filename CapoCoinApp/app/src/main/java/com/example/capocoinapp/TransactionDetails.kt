package com.example.capocoinapp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.ViewModels.TransactionViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.TransactionDetailsCard
import com.example.capocoinapp.designUI.components.rememberCategoryUI
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun TransactionsDetailsScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    transactionViewModel: TransactionViewModel,
    transactionID: Int?
) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 2) },
            pageTitle = "Transaction Details"
        ) { _ ->
            val transaction by transactionViewModel
                .getTransactionById(transactionID)
                .collectAsState(initial = null)

            if (transactionID != null) {
                transaction?.let { t ->
                    val (categoryColor, categoryIcon) =
                        rememberCategoryUI(t.categoryID, categoryViewModel)

                    Spacer(modifier = Modifier.height(16.dp))

                    CardBox(
                        cards = listOf(
                            {
                                TransactionDetailsCard(
                                    cardTitle = transaction?.transactionName ?: "null",
                                    cardSubTitle = transaction?.transactionDate ?: "null",
                                    cardAmount = transaction?.transactionAmount.toString(),
                                    cardSubAmount = transaction?.transactionTime ?: "null",
                                    categoryColor = categoryColor,
                                    categoryIcon = categoryIcon,
                                    cardTransactionType = transaction?.transactionType ?: "null",
                                    imageUri = transaction?.uploadedPhotoPath
                                )
                            })
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailsPreview() {
    CapoCoinAppTheme {
    }
}

