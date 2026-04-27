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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
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

@Composable
fun CategoryTotalRow(item: CategoryTotal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = item.categoryTitle,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "R ${String.format("%.2f", item.totalAmount)}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
    }
}