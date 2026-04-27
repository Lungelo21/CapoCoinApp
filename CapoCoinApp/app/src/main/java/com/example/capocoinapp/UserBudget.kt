package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.capocoinapp.Calculator.CalculatorViewModel
import com.example.capocoinapp.data.ViewModels.CategoryViewModel


@Composable
fun UserBudget(
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel,
    message: String = "",
    onEditClick:(
            minBudget:Double,
            maxBudget:Double
            )-> Unit = {_, _ ->},
    onAddCategoryClick: ()-> Unit = {}
){
    val viewModel = viewModel<CalculatorViewModel>()


}


