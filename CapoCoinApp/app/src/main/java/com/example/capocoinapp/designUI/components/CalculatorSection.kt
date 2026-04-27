package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capocoinapp.Calculator.CalculatorFunctions
import com.example.capocoinapp.Calculator.CalculatorOperation
import com.example.capocoinapp.Calculator.CalculatorState
import com.example.capocoinapp.Calculator.CalculatorViewModel
import com.example.capocoinapp.ui.theme.NavBarBG
import com.example.capocoinapp.ui.theme.Primary


@Composable
fun CalculatorSection(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 2.dp,
    onAction: (CalculatorFunctions) -> Unit
){
    Box (modifier = modifier.fillMaxSize()) {



        // keypad calc
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // display
            CalculatorDisplay(state)

            CalculatorButtons(
                state = state,
                onAction = onAction,
                buttonSpacing = buttonSpacing
            )
        }

        CalculatorButtonDesign (
            symbol = "Confirm Amount",
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .background(if(state.number1.isNotBlank()) Primary else NavBarBG),

            onClick = {
                if(state.number1.isNotBlank()){
                onAction(CalculatorFunctions.ConfirmAmount)
                }
            }
        )

    }
}

@Preview()
@Composable
fun CalculatorPreview(){
    CalculatorSection(
        state = CalculatorState(
            number1 = "1",
            number2 = "2",
            operation = CalculatorOperation.Add
        ),
        onAction = {},
        modifier = Modifier.fillMaxSize(),
        buttonSpacing = 8.dp
    )
}