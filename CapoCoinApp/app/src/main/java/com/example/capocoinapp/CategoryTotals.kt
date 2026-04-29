package com.example.capocoinapp

import android.app.DatePickerDialog
import android.graphics.Paint
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
import androidx.compose.material.icons.filled.CalendarMonth
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
import com.example.capocoinapp.designUI.components.WideButton
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.BackgroundColor

import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.TextWhite


@Composable
fun CategoryTotalsScreen(service: TransactionService, navController: NavHostController) {
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

    Box(modifier = Modifier.fillMaxSize())
    {
        //UI Formatting
        Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState))
        {
            //Row for Date Selection
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp))
            {
                // Start Date Button
                OutlinedButton(onClick = { showDatePicker(true) }, modifier = Modifier.weight(1f))
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
                OutlinedButton(onClick = { showDatePicker(false) }, modifier = Modifier.weight(1f))
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

            //Adding a Spacer between the Category Totals and Date Filtering functionality
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
                //Setting text for Category Name/Title Header
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                //Setting text for Category Total Amount Header
                Text(
                    text = "Total Amount",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }

            //Using a Horizontal divider to space out Category and Total Amount values
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
                //Checking if the totals are not populated
                if(totals.isEmpty())
                {
                    //Setting text to inform user of no Transactions found within relevant categories
                    Text(
                        text = "No transactions found for this period.",
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 8.dp)
                    )
                }
                else
                {
                    //Going through the list of saved total amounts for categories
                    totals.forEach { item ->

                        //Mapping all items with the unique ID to represent each individual category and its total
                        key(item.categoryTotalID)
                        {
                            CategoryTotalRow(item)
                        }
                    }
                }
            }

            //Adding Space to the page for a Back to User Budget  ensuring that the button
            //will not be covered by the Category totals
            Spacer(modifier = Modifier.height(100.dp))
        }

        Box(modifier = Modifier.padding(16.dp).fillMaxSize(), contentAlignment = Alignment.BottomCenter)
        {
            //Primary button call for a redirect to budget functionality
            WideButton(
                buttonText = "Back to Budget",
                onClick = {
                    // Navigates back to the Budget screen
                    navController.navigate("UserBudget")
                }
            )
        }

        //Add Spacer to ensure the button is away from the bottom edge from the screen
        Spacer(modifier = Modifier.height(16.dp))
    }
}

//Method which dictates the Format and what goes in each Row
@Composable
fun CategoryTotalRow(item: CategoryTotal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        //setting text for the specific Category identitifer
        Text(
            text = item.categoryTitle,
            style = MaterialTheme.typography.bodyLarge,
            color = TextWhite
        )
        //Setting text to the total amount of each category
        Text(
            text = "R ${String.format("%.2f", item.totalAmount)}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )
    }
}
