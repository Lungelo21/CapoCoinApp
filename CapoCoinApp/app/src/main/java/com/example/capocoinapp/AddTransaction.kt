package com.example.capocoinapp

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.Calculator.CalculatorViewModel
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.data.ViewModels.TransactionViewModel
import com.example.capocoinapp.data.dao.CategoryDAO
import com.example.capocoinapp.data.dao.TransactionsDAO
import com.example.capocoinapp.data.entities.Category
import com.example.capocoinapp.designUI.components.CalculatorSection
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.BackgroundColor
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.NavBarBG
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.RobotoSlab
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.AttachImageCard
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CalculatorButtonDesign
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.DatePickerCard
import com.example.capocoinapp.designUI.components.FinalAmountCard
import com.example.capocoinapp.designUI.components.SelectCategoryDropDown
import com.example.capocoinapp.designUI.components.SelectTransactionTypeDropDown
import com.example.capocoinapp.designUI.components.TimePickerCard
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.inputCard
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme


@Composable
fun AddTransaction() {
    // variable holds the calculator view model
    val calculatorViewModel = viewModel<CalculatorViewModel>()

    // stores the value for the state of the calculator view model
    val state = calculatorViewModel.state

    // title for the transaction
    var title by remember { mutableStateOf("") }

    // variable that holds the category viewmodel
    val catViewModel = viewModel<CategoryViewModel>()
    // stores the categories by retrieving the list of categories and storing them as an empty list state which is then filled
    val categories by catViewModel.getAllCategories().collectAsState(initial = emptyList())

    // selected category for the transaction
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    // variable that holds the transaction viewmodel
    val transactionViewModel = viewModel<TransactionViewModel>()
    // list of the transaction types
    val transactionTypes = listOf(
        "Income",
        "Expense",
        "Transfer"
    )
    // transaction type selected
    var chosenTransactionType by remember { mutableStateOf("") }

    // selected date from the date picker
    var selectedDate by remember { mutableStateOf("") }

    // selected time from the date picker
    var selectedTime by remember { mutableStateOf("") }

    // selected image (path)
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // amount which confirms if the confirm button in the calculator has been clicked
    var isAmountConfirmed = state.isAmountConfirmed

    CapoCoinAppTheme {
        val navController = rememberNavController()
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Add Transaction"
        ){ _ ->
            Column(modifier = Modifier.fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    // Dropdown for Transaction Type
                    SelectTransactionTypeDropDown(
                        transactionTypes = transactionTypes,
                        selectedTransactionType = chosenTransactionType,
                        onTransactionTypeSelected = { chosenTransactionType = it },
                        placeholderText = "Select Transaction Type",
                        enabled = isAmountConfirmed
                    )

                    // input for Transaction Title
                    inputCard(
                        value = title,
                        onValueChange = { title = it},
                        placeholder = "Add a title",
                        icon = Icons.Default.Edit,
                        enabled = isAmountConfirmed
                    )

                    // Dropdown for Category Selection
                    SelectCategoryDropDown(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it },
                        placeholderText = "Select Category",
                        enabled = isAmountConfirmed
                    )

                    // Add date of transaction
                    DatePickerCard(
                        selectedTransactionDate = selectedDate,
                        onTransactionDateSelected = { selectedDate = it},
                        placeholderText = "Select the date of Transaction",
                        enabled = isAmountConfirmed
                    )

                    // Add Time to transaction
                    TimePickerCard(
                        selectedTransactionTime = selectedTime,
                        onTransactionTimeSelected = { selectedTime = it},
                        placeholderText = "Select time of Transaction",
                        enabled = isAmountConfirmed
                    )

                    // Attach image button
                    AttachImageCard(
                        imageUri = selectedImageUri,
                        onImageSelected = { selectedImageUri = it},
                        placeholderText = "Attach Receipt or Salary Image",
                        enabled = isAmountConfirmed
                    )
                }

                // if the confirm amount in calculator hasnt been clicked yet (remains in calculator view)
                if(!isAmountConfirmed){

                    CalculatorSection(
                        state = state,
                        onAction = calculatorViewModel::onAction,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
                else // otherwise show rest of screen (without calculator)
                {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        // Button(Card) which shows the final card and its total
                        FinalAmountCard(
                            transactionAmount = state.number1,
                            cardIcon = Icons.Default.Calculate,
                            onAmountClicked = {
                                calculatorViewModel.reOpenCalculator()
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // log Transaction button
                        CalculatorButtonDesign(
                            symbol = "Log Transaction",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Primary),
                            // when Log Transaction is clicked passes the values to be entered into Transactions table
                            onClick = {
                                transactionViewModel.addTransaction(
                                    type = chosenTransactionType,
                                    name = title,
                                    amount = state.number1,
                                    categoryID = selectedCategory?.categoryID?: 0,
                                    date = selectedDate,
                                    time = selectedTime,
                                    photoPath = selectedImageUri?.toString()
                                )
                            }
                        )
                    }

                }
            }
        }
    }
}




