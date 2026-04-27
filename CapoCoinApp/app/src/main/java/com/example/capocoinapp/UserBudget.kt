package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.Calculator.CalculatorViewModel
import com.example.capocoinapp.data.ViewModels.CategoryViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme


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

    CapoCoinAppTheme {
        val navController = rememberNavController()
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "User Budget"
        ) { _ ->

        }
    }
}


