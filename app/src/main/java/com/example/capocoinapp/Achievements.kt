package com.example.capocoinapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.capocoinapp.data.ViewModels.AchievementViewModel
import com.example.capocoinapp.data.entities.Achievements
import com.example.capocoinapp.designUI.components.AppScaffold
import com.example.capocoinapp.designUI.components.BottomNavBar
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.TopNavBar
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.TextWhite

@Composable
    fun AchievementsScreen(
    navController: NavController,
    achievementViewModel: AchievementViewModel
) {
    val achievements by achievementViewModel
        .getAllAchievements(achievementViewModel.currentUserID)
        .collectAsState(initial = emptyList())

    CapoCoinAppTheme {
        AppScaffold(
            topBar = { TopNavBar(navController) },
            bottomBar = { BottomNavBar(navController, 4) },
            pageTitle = "User Achievements"
        ) { _ ->
            CardBox(
                cards = achievements.map { achievement ->
                    { AchievementCard(achievement = achievement) }
                }
            )
        }
    }
}

@Composable
fun AchievementCard(achievement: Achievements)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            //Displaying the achievement title
            Text(
                text = achievement.achievementTitle,
                style = CapoType.cardTitle,
                color = TextWhite
            )

            //Displaying the Date Achieved text
            Text(
                text = "Date Achieved",
                style = CapoType.cardSubTitle
            )
        }

        //Spacer to space out the card
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Displaying the Description
            Text(
                text = achievement.description,
                style = CapoType.cardSubTitle,
                color = TextWhite.copy(alpha = 0.7f),
                modifier = Modifier.weight(1f)
            )

            //Checks if achievement has been unlocked by checking date variable
            if(achievement.dateUnlocked.isNullOrEmpty()) {
                Text(
                    text = "Not Achieved yet",
                    style = CapoType.cardSubTitle
                )
            }
            else
            {
                Text(
                    text = achievement.dateUnlocked,
                    style = CapoType.cardSubTitle
                )
            }
        }
    }
}
}