package com.example.capocoinapp.Calculator

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null,

    val isConfirmed: Boolean = false
)
