package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.HomeCard
import com.example.capocoinapp.designUI.components.MilestoneAchievementCard
import com.example.capocoinapp.designUI.components.PageSubTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.UserProfileCard
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun UserProfileScreen(navController: NavController) {
    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "Home"
        ) { _ ->

            //val firstName by remember{ mutableStateOf() }

            CardBox(
                cards = listOf(
                    {
                        UserProfileCard("Harry", "Davis", 4, "Penny Pincher", 100, 1100)
                    },

                    {
                        PageSubTitleText("Recent Milestones")
                    },

                    {
                        MilestoneAchievementCard("Weekly Logger", "Log Expenses everyday for a week", "5")
                    }
                )
            )
        }
    }
}