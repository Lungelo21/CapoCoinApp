package com.example.capocoinapp.Calculator

sealed class CalculatorOperation(val symbol: String){

    /*
    * Author: Phillip Lackner
    * Link: https://www.youtube.com/watch?v=-aTcFJWxEQA
    * DateAccessed: 14/04/2026
    * */

    object Add: CalculatorOperation("+")
    object Subtract: CalculatorOperation("-")
    object Multiply: CalculatorOperation("*")
    object Divide: CalculatorOperation("/")

}
