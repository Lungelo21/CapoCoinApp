package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

//ToDo: this screen will need to be updated as more screens are added
@Composable
fun MoreScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "More"
        ) { _ ->

            CardBox(
                cards = listOf(

                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CapoCoinAppTheme {
        val navController = rememberNavController()
        MoreScreen(navController)
    }
}