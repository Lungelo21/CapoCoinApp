package com.example.capocoinapp.Calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())

        private set

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    // on action what occurs per action clicked
    fun onAction(action: CalculatorFunctions){
        when(action){
            // enters number as the enterNumber button is clicked
            is CalculatorFunctions.Number -> enterNumber(action.number)
            // adds a decimal as the enterDecimal button is clicked
            is CalculatorFunctions.DecimalFunction -> enterDecimal()
            // clears the calculation as the clear button is clicked
            is CalculatorFunctions.ClearFunction -> {state = CalculatorState()}
            // enters the corresponding operation per operation button clicked
            is CalculatorFunctions.Operation -> enterOperation(action.operation)
            // calculates the result of the calculation when = is clicked
            is CalculatorFunctions.CalculateFunction -> doCalculation()
            // deletes the most recently entered number when del button is clicked
            is CalculatorFunctions.DeleteFunction -> doDelete()
            // confirms the amount for the user to enter for the transaction when confirm is clicked
            is CalculatorFunctions.ConfirmAmount -> confirmTransactionAmount()
        }
    }

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    // delete function
    private fun doDelete() {
        // ensures that when user is deleting, the state of amount confirmed and editing amount are false
        state = state.copy(isAmountConfirmed = false, isShowingResult = false)

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

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    // enter decimal function
    private fun enterDecimal(){

        // reset fields
        if(state.isShowingResult){
            state = state.copy(
                number1 = "",
                isAmountConfirmed = false,
                isShowingResult = false
            )
        }


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

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    // enter number function
    private fun enterNumber(number: Int) {

        // checking if the user is coming back to make an edit, if they are then reset field
        if(state.isShowingResult){
            state = state.copy(
                number1 = "",
                number2 = "",
                operation = null,
                isAmountConfirmed = false,
                isShowingResult = false
            )
        }

        // checks if operation is null
        if(state.operation == null){

            // if length of num1 is greater than or equal to 8 characters
            if(state.number1.length >= MAX_NUM_LENGTH){
                return // return them
            }

            state = state.copy(
                number1 = state.number1 + number,
                isAmountConfirmed = false
            )
            return
        }

        // if length of num2 is greater than or equal to 8 characters
        if(state.number2.length >= MAX_NUM_LENGTH){
            return // return them
        }
        state = state.copy(
            number2 = state.number2 + number,
            isAmountConfirmed = false
        )
    }

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    // companion object to store maximum length of a num
    companion object{
        private const val MAX_NUM_LENGTH = 8
    }

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    // enter operation function
    private fun enterOperation(operation: CalculatorOperation){
        // checks state of number1 isnt blank
        if(state.number1.isNotBlank()){
            // sets operation to operation
            state = state.copy(
                operation = operation,
                isAmountConfirmed = false,
                isShowingResult = false
            )
        }
    }

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    // calculation function
    private fun doCalculation(){

        // storing value of number 1 and 2
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        // checks if numbers arent null
        if(number1 != null && number2 != null){

            // result based off of operation
            val result = when(state.operation){
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Divide -> number1/number2
                is CalculatorOperation.Multiply -> number1 * number2

                null -> return
            }

            // sets the calculated amount to 2 decimal places
            state = state.copy(
                number1 = String.format("%.2f", result),
                number2 = "",

                operation = null
            )
        }
    }

    // confirm amount for transaction function
    private fun confirmTransactionAmount(){

        // stores vals for number 1 and 2
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        // final amount based off condition
        val finalAmount = when{

            // when user has made a calculation and gotten a result
            state.number2.isBlank() && state.operation == null ->
                state.number1.toDoubleOrNull()

            // checks if number 1 and 2 isnt null
            number1 != null && number2 != null -> {

                // calculates based off operation
                when(state.operation){
                    is CalculatorOperation.Add -> number1 + number2
                    is CalculatorOperation.Subtract -> number1 - number2
                    is CalculatorOperation.Divide -> number1/number2
                    is CalculatorOperation.Multiply -> number1 * number2
                    null -> number1
                }
            }
            // final amount is otherwise the first number entered
            else -> number1
        }
        if(finalAmount == null) return

        state = state.copy( // final amount is stored
            number1 = String.format("%.2f", finalAmount),
            number2 = "",
            operation = null,
            isAmountConfirmed = true,
            isShowingResult = true
        )
    }

    fun reOpenCalculator(){
        state = state.copy(isAmountConfirmed = false)
    }

}