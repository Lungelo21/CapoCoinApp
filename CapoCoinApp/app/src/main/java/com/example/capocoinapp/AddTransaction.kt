package com.example.capocoinapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capocoinapp.Calculator.CalculatorFunctions
import com.example.capocoinapp.Calculator.CalculatorOperation
import com.example.capocoinapp.Calculator.CalculatorState
import com.example.capocoinapp.Calculator.CalculatorViewModel
import com.example.capocoinapp.designUI.components.CalculatorSection
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.BackgroundColor
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.NavBarBG
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.RobotoSlab


@Composable
fun AddTransaction(){
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state

    var isAmountConfirmed by remember { mutableStateOf(false) }

    var showCalculator by remember{ mutableStateOf(true) }

//    CapoCoinAppTheme{
//        AppScaffold(
//            topBar = { TopNavBar() },
//            bottomBar = { BottomNavBar() },
//            pageTitle = "Add Transaction"
//        )
//    }

    Column(modifier = Modifier.fillMaxSize()){

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
            ){
                Text("Add a Transaction", color = Accent, fontFamily = RobotoSlab, fontWeight = FontWeight.Bold)


            }

        if(!isAmountConfirmed){

            CalculatorSection(
                state = state,
                onAction = viewModel::onAction,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

//            ConfirmButton{
//                isAmountConfirmed = true
//            }
        }
        else{

//            FinalAmountSection(
//                state = state
//            )

//            AddTransactionButton{
//
//            }
        }


    }
}

