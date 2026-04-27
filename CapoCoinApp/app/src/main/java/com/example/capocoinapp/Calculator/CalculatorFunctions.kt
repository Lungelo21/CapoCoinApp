package com.example.capocoinapp.Calculator

sealed class CalculatorFunctions{
    data class Number(val number: Int): CalculatorFunctions()
    object ClearFunction: CalculatorFunctions()
    object DeleteFunction: CalculatorFunctions()
    object DecimalFunction: CalculatorFunctions()
    object CalculateFunction: CalculatorFunctions()
    data class Operation(val operation: CalculatorOperation): CalculatorFunctions()
}
