package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.capocoinapp.Calculator.CalculatorFunctions
import com.example.capocoinapp.Calculator.CalculatorOperation
import com.example.capocoinapp.Calculator.CalculatorState
import com.example.capocoinapp.designUI.components.CalculatorButtonDesign
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.NavBarBG
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.PurpleGrey40


@Composable
fun CalculatorButtons(
    state: CalculatorState,
    onAction: (CalculatorFunctions) -> Unit,
    buttonSpacing: Dp,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Calculator button for AC
            CalculatorButtonDesign(
                symbol = "AC",
                modifier = Modifier
                    .background(NavBarBG)
                    .aspectRatio(2f)
                    .weight(1.6f),

                onClick = {
                    onAction(CalculatorFunctions.ClearFunction)
                }
            )
            // Calculator button for Del
            CalculatorButtonDesign(
                symbol = "Del",
                modifier = Modifier
                    .background(Primary)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.DeleteFunction)
                }
            )
            // Calculator button for /
            CalculatorButtonDesign(
                symbol = "/",
                modifier = Modifier
                    .background(Primary)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Operation(CalculatorOperation.Divide))
                }
            )
        }

        // Row 2 of Calculator(7, 8, 9, x)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Calculator button for 7
            CalculatorButtonDesign(
                symbol = "7",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(7))
                }
            )

            // Calculator button for 8
            CalculatorButtonDesign(
                symbol = "8",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(8))
                }
            )

            // Calculator button for 9
            CalculatorButtonDesign(
                symbol = "9",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(9))
                }
            )

            // Calculator button for x
            CalculatorButtonDesign(
                symbol = "x",
                modifier = Modifier
                    .background(Primary)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Operation(CalculatorOperation.Multiply))
                }
            )
        }

        // Row 3 of Calculator(4, 5, 6, -)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Calculator button for 4
            CalculatorButtonDesign(
                symbol = "4",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(4))
                }
            )

            // Calculator button for 5
            CalculatorButtonDesign(
                symbol = "5",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(5))
                }
            )

            // Calculator button for 6
            CalculatorButtonDesign(
                symbol = "6",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(6))
                }
            )

            // Calculator button for -
            CalculatorButtonDesign(
                symbol = "-",
                modifier = Modifier
                    .background(Primary)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Operation(CalculatorOperation.Subtract))
                }
            )
        }

        // Row 4 of Calculator(1, 2, 3, +)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Calculator button for 1
            CalculatorButtonDesign(
                symbol = "1",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(1))
                }
            )

            // Calculator button for 2
            CalculatorButtonDesign(
                symbol = "2",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(2))
                }
            )

            // Calculator button for 3
            CalculatorButtonDesign(
                symbol = "3",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Number(3))
                }
            )

            // Calculator button for +
            CalculatorButtonDesign(
                symbol = "+",
                modifier = Modifier
                    .background(Primary)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.Operation(CalculatorOperation.Add))
                }
            )
        }

        // Row 5 of Calculator(0, ., =)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {

            // Calculator button for 0
            CalculatorButtonDesign(
                symbol = "0",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(2f)
                    .weight(1.6f),

                onClick = {
                    onAction(CalculatorFunctions.Number(0))
                }
            )

            // Calculator button for .
            CalculatorButtonDesign(
                symbol = ".",
                modifier = Modifier
                    .background(CardBG)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.DecimalFunction)
                }
            )

            // Calculator button for =
            CalculatorButtonDesign(
                symbol = "=",
                modifier = Modifier
                    .background(Primary)
                    .aspectRatio(1f)
                    .weight(0.8f),

                onClick = {
                    onAction(CalculatorFunctions.CalculateFunction)
                }
            )
        }

    }

}
