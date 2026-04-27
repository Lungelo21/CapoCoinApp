package com.example.capocoinapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capocoinapp.Calculator.CalculatorViewModel
import com.example.capocoinapp.designUI.components.CalculatorSection


@Composable
fun AddTransaction(){
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state

    var showCalculator by remember{ mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()){

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ){
//            AddTransactionTopContent(
//                state = state,
//                onConfirm = {
//                    showCalculator = false
//                }
//            )

        }

        if(showCalculator){
            CalculatorSection(
                state = state,
                onAction = viewModel::onAction,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }
    }
}

