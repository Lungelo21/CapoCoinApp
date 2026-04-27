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

@Composable
fun CategoryTotalsScreen(service: TransactionService) {
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
                Text(text = if (startDate.isEmpty()) "Start Date" else "From: $startDate")
            }
            // End Date Button
            OutlinedButton(onClick = { showDatePicker(false) }, modifier = Modifier.weight(1f))
            {
                Text(text = if (endDate.isEmpty()) "End Date" else "To: $endDate")
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
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "R ${String.format("%.2f", item.totalAmount)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
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

    Column(modifier = Modifier.fillMaxSize().padding(16.dp))
    {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            OutlinedButton(onClick = {}, modifier = Modifier.weight(1f))
            {
                Text("From: 2026-04-01")
            }
            OutlinedButton(onClick = {}, modifier = Modifier.weight(1f))
            {
                Text("To: 2026-04-27")
            }
        }

        //Spacer to space out page
        Spacer(modifier = Modifier.height(24.dp))

        //Added headers to indentify category and total amount
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
        Column(modifier = Modifier.padding(top = 8.dp)) {
            mockTotals.forEach { item ->
                CategoryTotalRow(item)
            }
        }

    }
}