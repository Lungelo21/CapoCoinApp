package com.example.capocoinapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.capocoinapp.designUI.components.CardBox
import com.example.capocoinapp.designUI.components.CardComponent
import com.example.capocoinapp.designUI.components.HeadingText
import com.example.capocoinapp.ui.theme.BackgroundColor
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme

@Composable
fun MoreScreen() {
    CapoCoinAppTheme() {
        Surface(
            color = BackgroundColor,
            modifier = Modifier.fillMaxSize()
        ) {
            //ToDo: top and bottom nav bar
            CardBox(
                cards = listOf(
                    {
                        HeadingText("More")
                    },
                    {
                        CardComponent(
                            "Dinner Night",
                            "Empire Steak",
                            "- R200",
                            "5:00 PM",
                            Icons.Default.Fastfood,
                            "expense"
                        )
                    },
                    {
                        CardComponent(
                            "Movie",
                            "Pavillion",
                            "- R150",
                            "7:45 AM",
                            Icons.Default.Movie,
                            "expense"
                        )
                    },
                    {
                        CardComponent(
                            "Salary",
                            "Dunder Mifflin",
                            "+ R30 000",
                            "9:45 AM",
                            Icons.Default.Payments,
                            "income"
                        )
                    }
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CapoCoinAppTheme {
        MoreScreen()
    }
}