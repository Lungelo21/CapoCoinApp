package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.PageTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun AnalyticsScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Home"
        ) { _ ->

            //ToDo: add graph generator

            PageTitleText("Analytics coming soon")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnalyticsPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        AnalyticsScreen(navController)
    }
}