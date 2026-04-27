package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capocoinapp.Calculator.CalculatorFunctions
import com.example.capocoinapp.Calculator.CalculatorOperation
import com.example.capocoinapp.Calculator.CalculatorState
import com.example.capocoinapp.Calculator.CalculatorViewModel


@Composable
fun CalculatorSection(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 2.dp,
    onAction: (CalculatorFunctions) -> Unit
){
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorDisplay(state)

            CalculatorButtons(
                onAction = onAction,
                buttonSpacing = buttonSpacing
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview(){
    CalculatorSection(
        state = CalculatorState(
            number1 = "1",
            number2 = "2",
            operation = CalculatorOperation.Add
        ),
        onAction = {},
        buttonSpacing = 8.dp
    )
}