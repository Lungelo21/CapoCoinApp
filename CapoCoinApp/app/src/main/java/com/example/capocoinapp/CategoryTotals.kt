package com.example.capocoinapp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.capocoinapp.Services.TransactionService

import androidx.compose.ui.platform.LocalContext

//
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.padding

import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.material3.OutlinedButton

import androidx.compose.material3.Text

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll

import androidx.compose.runtime.key

import com.example.capocoinapp.data.dto.CategoryTotal

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.DatePickerCard
import com.example.capocoinapp.designUI.components.PrimaryButton
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.BackgroundColor

import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.TextWhite


@Composable
fun CategoryTotalsScreen(service: TransactionService, navController: NavHostController) {
    val context = LocalContext.current

    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }

    val scrollState = androidx.compose.foundation.rememberScrollState()

    val showDatePicker = { isStartDate: Boolean ->
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
    }

    //UI Formatting
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState))
    {
        //Row for Date Selection
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            // Start Date Button
            OutlinedButton(onClick = { showDatePicker(true) }, modifier = Modifier.weight(1f))
            {
                Text(text = if (startDate.isEmpty()) "Start Date" else "From: $startDate", color = TextWhite)
            }
            // End Date Button
            OutlinedButton(onClick = { showDatePicker(false) }, modifier = Modifier.weight(1f))
            {
                Text(text = if (endDate.isEmpty()) "End Date" else "To: $endDate", color = TextWhite)
            }
        }

        //Check to ensure Clear Filters button wont appear if no filter has been made
        if(startDate.isNotEmpty() || endDate.isNotEmpty())
        {
            OutlinedButton(onClick = {
                startDate = ""
                endDate = ""
            },
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text("Clear All filters", color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        //Updated data when dates are changed
        val totals by service.getCategoryTotals(startDate, endDate)
            .collectAsState(initial = emptyList())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Category",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Total Amount",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 8.dp),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.3f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        {
            if(totals.isEmpty())
            {
                Text(
                    text = "No transactions found for this period.",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 8.dp)
                )
            }
            else
            {
                totals.forEach { item ->

                    key(item.categoryTotalID)
                    {
                        CategoryTotalRow(item)
                    }
                }
            }
        }

    }
}

@Composable
fun CategoryTotalRow(item: CategoryTotal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.categoryTitle,
            style = MaterialTheme.typography.bodyLarge,
            color = TextWhite
        )
        Text(
            text = "R ${String.format("%.2f", item.totalAmount)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCategoryTotals()
{
    val mockItem = CategoryTotal(
        categoryTotalID = 1,
        categoryTitle = "Groceries",
        totalAmount = 1250.50
    )

    // Wrap in a Column just to see it clearly in the preview pane
    Column(modifier = Modifier.padding(16.dp)) {
        CategoryTotalRow(item = mockItem)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategoryTotalsScreen() {
    // Create a list of dummy data
    val mockTotals = listOf(
        CategoryTotal(1, "Transport", 450.00),
        CategoryTotal(2, "Food", 1200.00),
        CategoryTotal(3, "Rent", 5000.00),
        CategoryTotal(4, "Entertainment", 300.00)
    )

    CapoCoinAppTheme {
        Scaffold(
            topBar = {
                // Using a simple header to match your app's style
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .padding(16.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = "Category Totals",
                        style = CapoType.cardTitle,
                        fontSize = 24.sp,
                        color = Accent
                    )
                }
            },
            bottomBar = {
                // This mimics your navigation bar
                NavigationBar(
                    containerColor = CardBG
                ) {
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Default.Payments,
                                contentDescription = null,
                                tint = TextWhite
                            )
                        },
                        label = { Text("Totals", color = TextWhite) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Default.History,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        },
                        label = { Text("History", color = Color.Gray) }
                    )
                }
            },
            containerColor = BackgroundColor // Uses your theme's background
        )
        { innerPadding ->
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start)
            {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
                {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        PrimaryButton (
                            buttonText = "From: 2026-04-01", // Pass string directly
                            onClick = {}
                        )
                    }
                    
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        PrimaryButton (
                            buttonText = "To: 2026-04-27", // Pass string directly
                            onClick = {}
                        )
                    }
                }

                //Spacer to space out page
                Spacer(modifier = Modifier.height(24.dp))

                //Added headers to identify category and total amount
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Total Amount",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.3f)
                )

                //Column instantiating each increment (category) and its total amount
                Column(modifier = Modifier.padding(top = 8.dp))
                {
                    mockTotals.forEach { item ->
                        CategoryTotalRow(item)
                    }
                }

            }
        }
    }
}