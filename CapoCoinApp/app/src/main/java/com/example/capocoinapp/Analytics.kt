package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.PageTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun AnalyticsScreen() {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar() },
            bottomBar = { BottomNavBar() },
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
        AnalyticsScreen()
    }
}