package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.capocoinapp.data.ViewModels.UserViewModel
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.MilestoneAchievementCard
import com.example.capocoinapp.designUI.components.PageSubTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.UserProfileCard
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun UserProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel) {

    val users by userViewModel
        .getAllUsers()
        .collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController,4) },
            pageTitle = "Home"
        ) { _ ->

            //ToDo: add remaining values for user levels
            users.forEach { user ->
                CardBox(
                    cards = listOf(
                        {
                            UserProfileCard(
                                user.name,
                                "Davis",
                                4,
                                "Penny Pincher",
                                100,
                                1100
                            )
                        },

                        {
                            PageSubTitleText("Recent Milestones")
                        },

                        {
                            MilestoneAchievementCard(
                                "Weekly Logger",
                                "Log Expenses everyday for a week",
                                "5"
                            )
                        }
                    )
                )
            }
        }
    }
}