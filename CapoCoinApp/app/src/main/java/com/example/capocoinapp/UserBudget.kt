package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UserBudget(
    modifier: Modifier = Modifier,
    message: String = "",
    onEditClick:(
            minBudget:Double,
            maxBudget:Double
            )-> Unit = {_, _ ->},
    onAddCategoryClick: ()-> Unit = {}
){

}