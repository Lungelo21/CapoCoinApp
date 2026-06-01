package com.example.capocoinapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.capocoinapp.data.ViewModels.UserDetailsViewModel
import com.example.capocoinapp.data.ViewModels.UserViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun UserDetails(
    navController: NavController,
    userDetailsViewModel: UserDetailsViewModel
){

    val userDetails by

    CapoCoinAppTheme {

        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 2) },
            pageTitle = "Add Transaction"
        ) { _ ->
            Column(modifier = Modifier.fillMaxSize())
            {

            }
        }
    }
}