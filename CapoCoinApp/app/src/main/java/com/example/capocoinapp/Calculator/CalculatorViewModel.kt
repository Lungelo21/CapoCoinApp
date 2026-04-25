package com.example.capocoinapp.Calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())

        private set

    fun onAction(action: CalculatorFunctions){
        when(action){
            is CalculatorFunctions.Number -> enterNumber(action.number)
            is CalculatorFunctions.DecimalFunction -> enterDecimal()
            is CalculatorFunctions.ClearFunction -> CalculatorState()
            is CalculatorFunctions.Operation -> enterOperation(action.operation)
            is CalculatorFunctions.CalculateFunction -> doCalculation()
            is CalculatorFunctions.DeleteFunction -> doDelete()
        }
    }

    private fun doDelete() {
        when{
            // check if state number 2 isnt blank, if it isnt blank, then removes the 2nd number's state, then updates the state of the number
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )

            // checks if state operation isnt null, if it isnt null, then removes the state of operation, then updates the state of operation
            state.operation != null -> state = state.copy(
                operation = null
            )
            // check if state number 1 isnt blank, if it isnt blank, then removes the 1st number's state, then updates the state of the number
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun enterDecimal(){
        // makes the checks to see if to apply the decimal place to the first number entered into the calculator
        if(state.operation == null && !state.number1.contains(".")
            && state.number1.isNotBlank()
            ) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }

        // makes the checks to see if to apply the decimal place to the second number entered into the calculator
        if(!state.number2.contains(".") && state.number2.isNotBlank()
        ) {
            state = state.copy(
                number2 = state.number2 + "."
            )
            return
        }

    }

    private fun enterNumber(number: Int) {
        if(state.operation == null){

            if(state.number1.length >= MAX_NUM_LENGTH){
                return
            }

            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }

        if(state.number2.length >= MAX_NUM_LENGTH){
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }

    companion object{
        private const val MAX_NUM_LENGTH = 8
    }

    private fun enterOperation(operation: CalculatorOperation){
        if(state.number1.isNotBlank()){
            state = state.copy(operation = operation)
        }
    }

    private fun doCalculation(){
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        if(number1 != null && number2 != null){

            val result = when(state.operation){
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Divide -> number1/number2
                is CalculatorOperation.Multiply -> number1 * number2

                null -> return
            }

            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",

                operation = null
            )
        }
    }


}