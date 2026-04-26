package com.example.capocoinapp.Calculator

sealed class CalculatorFunctions{

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    data class Number(val number: Int): CalculatorFunctions()
    object ClearFunction: CalculatorFunctions()
    object DeleteFunction: CalculatorFunctions()
    object DecimalFunction: CalculatorFunctions()
    object CalculateFunction: CalculatorFunctions()
    data class Operation(val operation: CalculatorOperation): CalculatorFunctions()
}
