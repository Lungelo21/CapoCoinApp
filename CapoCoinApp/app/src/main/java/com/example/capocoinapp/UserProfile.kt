package com.example.capocoinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.capocoinapp.data.ViewModels.UserViewModel
import com.example.capocoinapp.data.entities.Category
import com.example.capocoinapp.data.entities.User
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
fun UserProfileScreen(navController: NavController, userViewModel: UserViewModel) {


    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController) },
            pageTitle = "User Profile"
        ) { _ ->

            //val firstName by remember{ mutableStateOf() }

            CardBox(
                cards = listOf(
                    {
                        UserProfileCard(
                            firstName = "Harry",
                            lastName = "Davis",
                            level = 4,
                            profileTitle = "Penny Pincher",
                            currentXP = 100,
                            nextLevelXP = 1100
                        )
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