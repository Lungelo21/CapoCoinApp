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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.capocoinapp.data.ViewModels.AchievementViewModel
import com.example.capocoinapp.data.entities.Achievements
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.MilestoneAchievementCard
import com.example.capocoinapp.designUI.components.PageSubTitleText
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.designUI.components.UserProfileCard
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import kotlin.collections.emptyList
@Composable
fun UserProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    achievementsViewModel: AchievementViewModel) {

    val users: List<User> by userViewModel
        .getAllUsers()
        .collectAsState(initial = emptyList())

    val currentUser:User? = users.firstOrNull()

    val achievements: List<Achievements> by achievementsViewModel
        .getAllAchievements(currentUser?.id ?: "")
        .collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController,4) },
            pageTitle = "Profile"
        ) { _ ->

            if (currentUser != null) {

                CardBox(
                    cards = listOf(

                        {
                            UserProfileCard(
                                name = currentUser.name,
                                username=currentUser.username,
                                4,
                                "Penny Pincher",
                                100,
                                1100,
                                onClick = {
                                    // Navigates to UserDetails after onClick
                                    navController.navigate("UserDetails")
                                }
                            )
                        },

                        {
                            PageSubTitleText("Recent Achievements")
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

            } else {

                CardBox(
                    cards = listOf(
                        {
                            PageSubTitleText(
                                "No user profile found. Please login first."
                            )
                        }
                    )
                )

            }

        }
    }
}