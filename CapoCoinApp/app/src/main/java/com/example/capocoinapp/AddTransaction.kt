package com.example.capocoinapp

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state

    var title by remember { mutableStateOf("") }

    val catViewModel = viewModel<CategoryViewModel>()
    // stores the categories by retrieving the list of categories and storing them as an empty list state which is then filled
    val categories by catViewModel.getAllCategories().collectAsState(initial = emptyList())

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    val transactionViewModel = viewModel<TransactionViewModel>()
    // transaction types
    val transactionTypes = listOf(
        "Income",
        "Expense",
        "Transfer"
    )

    var chosenTransactionType by remember { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf("") }

    var selectedTime by remember { mutableStateOf("") }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    var isAmountConfirmed by remember { mutableStateOf(false) }

    var showCalculator by remember { mutableStateOf(true) }

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

                    DatePickerCard(
                        selectedTransactionDate = selectedDate,
                        onTransactionDateSelected = { selectedDate = it},
                        placeholderText = "Select the date of Transaction",
                        enabled = isAmountConfirmed
                    )

                    TimePickerCard(
                        selectedTransactionTime = selectedTime,
                        onTransactionTimeSelected = { selectedTime = it},
                        placeholderText = "Select time of Transaction",
                        enabled = isAmountConfirmed
                    )

                    AttachImageCard(
                        imageUri = selectedImageUri,
                        onImageSelected = { selectedImageUri = it},
                        placeholderText = "Attach Receipt or Salary Image",
                        enabled = isAmountConfirmed
                    )
                }


                if(!isAmountConfirmed){

                    CalculatorSection(
                        state = state,
                        onAction = viewModel::onAction,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
                else
                {

                    FinalAmountCard(
                        transactionAmount = state.number1,
                        cardIcon = Icons.Default.Calculate,
                        onAmountClicked = {
                             viewModel.reOpenCalculator()
                        }
                    )
                }
            }
        }


        if(!isAmountConfirmed){

            CalculatorSection(
                state = state,
                onAction = viewModel::onAction,
                modifier = Modifier
                    .fillMaxWidth()
            )


    //            ConfirmButton{
    //                isAmountConfirmed = true
    //            }
        }
        else {

    //            FinalAmountSection(
    //                state = state
    //            )

    //            AddTransactionButton{
    //
    //            }
        }
    }
}




