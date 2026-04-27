package com.example.capocoinapp

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capocoinapp.Calculator.CalculatorViewModel
import com.example.capocoinapp.designUI.components.CalculatorSection
import com.example.capocoinapp.ui.theme.Accent
import com.example.capocoinapp.ui.theme.BackgroundColor
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.NavBarBG
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.RobotoSlab
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.inputCard


@Composable
fun AddTransaction() {
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state

    var title by remember { mutableStateOf("") }

    var isAmountConfirmed by remember { mutableStateOf(false) }

    var showCalculator by remember { mutableStateOf(true) }

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar() },
            bottomBar = { BottomNavBar() },
            pageTitle = "Add Transaction"
        ) { _ ->
//
//            Column(modifier = Modifier.fillMaxSize()){
//
//                Column(modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            }
//
//            if(!isAmountConfirmed){
//
//                CalculatorSection(
//                    state = state,
//                    onAction = viewModel::onAction,
//                    modifier = Modifier
//                        .weight(1f)
//                        .fillMaxWidth()
//                )
//
////            ConfirmButton{
////                isAmountConfirmed = true
////            }
//            }
//            else{
//
////            FinalAmountSection(
////                state = state
////            )
//
////            AddTransactionButton{
////
////            }
//            }
//
//
//        }
//    }
//
//            inputCard(
//                value = title,
//                onValueChange = { title = it},
//                placeholder = "Add a title",
//                icon = Icons.Default.Edit,
//                enabled = isAmountConfirmed
//            )
//          }
        }
    }
}



