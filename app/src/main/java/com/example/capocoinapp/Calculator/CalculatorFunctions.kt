package com.example.capocoinapp.Calculator

sealed class CalculatorFunctions{

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */
    // Stores calculator number
    data class Number(val number: Int): CalculatorFunctions()
    // clear function on the calculator
    object ClearFunction: CalculatorFunctions()
    // delete function on the calculator
    object DeleteFunction: CalculatorFunctions()
    // decimal function on the calculator
    object DecimalFunction: CalculatorFunctions()
    // calculate function on the calculator
    object CalculateFunction: CalculatorFunctions()
    // Operation data class passing CalculatorOperation as a parameter
    data class Operation(val operation: CalculatorOperation): CalculatorFunctions()
    // confirm amount(Not really calculator functionality)
    object ConfirmAmount: CalculatorFunctions()
}
