package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.capocoinapp.Calculator.CalculatorFunctions
import com.example.capocoinapp.Calculator.CalculatorOperation
import com.example.capocoinapp.Calculator.CalculatorState
import com.example.capocoinapp.ui.theme.NavBarBG
import com.example.capocoinapp.ui.theme.Primary


@Composable
fun CalculatorSection(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 4.dp,
    onAction: (CalculatorFunctions) -> Unit
){

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                //display
                CalculatorDisplay(
                    state = state,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f)
                )

                CalculatorButtons(
                    state = state,
                    onAction = onAction,
                    buttonSpacing = buttonSpacing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.7f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            CalculatorButtonDesign(
                symbol = "Confirm Amount",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
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