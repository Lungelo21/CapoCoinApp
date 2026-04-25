package com.example.capocoinapp.uiScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capocoinapp.Calculator.CalculatorFunctions
import com.example.capocoinapp.Calculator.CalculatorOperation
import com.example.capocoinapp.Calculator.CalculatorState
import com.example.capocoinapp.designUI.components.CalculatorButton
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.NavBarBG
import com.example.capocoinapp.ui.theme.Primary

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorFunctions) -> Unit
){
    Box(modifier = modifier){
        Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ){
            Text(
                text = state.number1 + (state.operation ?: "") + state.number2,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Light,
                fontSize = 80.sp,
                color = Color.White,
                maxLines = 2
            )

            // Row 1 of Calculator(AC, Del, /)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                // Calculator button for AC
                CalculatorButton(
                    symbol = "AC",
                    modifier = Modifier
                        .background(NavBarBG)
                        .aspectRatio(2f)
                        .weight(2f),

                    onClick = {
                        onAction(CalculatorFunctions.ClearFunction)
                    }
                )
                // Calculator button for Del
                CalculatorButton(
                    symbol = "Del",
                    modifier = Modifier
                        .background(Primary)
                        .aspectRatio(1f)
                        .weight(2f),

                    onClick = {
                        onAction(CalculatorFunctions.DeleteFunction)
                    }
                )
                // Calculator button for /
                CalculatorButton(
                    symbol = "/",
                    modifier = Modifier
                        .background(Primary)
                        .aspectRatio(2f)
                        .weight(2f),

                    onClick = {
                        onAction(CalculatorFunctions.Operation(CalculatorOperation.Divide))
                    }
                )
            }

            // Row 2 of Calculator(7, 8, 9, x)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                // Calculator button for 7
                CalculatorButton(
                    symbol = "7",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(7))
                    }
                )

                // Calculator button for 8
                CalculatorButton(
                    symbol = "8",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(8))
                    }
                )

                // Calculator button for 9
                CalculatorButton(
                    symbol = "9",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(9))
                    }
                )

                // Calculator button for x
                CalculatorButton(
                    symbol = "x",
                    modifier = Modifier
                        .background(Primary)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Operation(CalculatorOperation.Multiply))
                    }
                )
            }

            // Row 3 of Calculator(4, 5, 6, -)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                // Calculator button for 4
                CalculatorButton(
                    symbol = "4",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(4))
                    }
                )

                // Calculator button for 5
                CalculatorButton(
                    symbol = "5",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(5))
                    }
                )

                // Calculator button for 6
                CalculatorButton(
                    symbol = "6",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(6))
                    }
                )

                // Calculator button for -
                CalculatorButton(
                    symbol = "-",
                    modifier = Modifier
                        .background(Primary)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Operation(CalculatorOperation.Subtract))
                    }
                )
            }

            // Row 4 of Calculator(1, 2, 3, +)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){
                // Calculator button for 1
                CalculatorButton(
                    symbol = "1",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(1))
                    }
                )

                // Calculator button for 2
                CalculatorButton(
                    symbol = "2",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(2))
                    }
                )

                // Calculator button for 3
                CalculatorButton(
                    symbol = "3",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(3))
                    }
                )

                // Calculator button for +
                CalculatorButton(
                    symbol = "+",
                    modifier = Modifier
                        .background(Primary)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.Operation(CalculatorOperation.Add))
                    }
                )
            }

            // Row 5 of Calculator(0, ., =)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ){

                // Calculator button for 0
                CalculatorButton(
                    symbol = "0",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(2f)
                        .weight(2f),

                    onClick = {
                        onAction(CalculatorFunctions.Number(0))
                    }
                )

                // Calculator button for .
                CalculatorButton(
                    symbol = ".",
                    modifier = Modifier
                        .background(CardBG)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.DecimalFunction)
                    }
                )

                // Calculator button for =
                CalculatorButton(
                    symbol = "=",
                    modifier = Modifier
                        .background(Primary)
                        .aspectRatio(1f)
                        .weight(1f),

                    onClick = {
                        onAction(CalculatorFunctions.CalculateFunction)
                    }
                )


            }
        }
    }
}