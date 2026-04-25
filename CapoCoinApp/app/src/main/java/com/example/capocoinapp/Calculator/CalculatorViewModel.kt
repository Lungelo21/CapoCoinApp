package com.example.capocoinapp.Calculator

import android.view.View
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
            is CalculatorFunctions.Number -> enterNumber(action.number)
        }
    }
}