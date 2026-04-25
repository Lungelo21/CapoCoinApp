package com.example.capocoinapp.designUI.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.capocoinapp.ui.theme.BackgroundColor
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.TextGreen
import com.example.capocoinapp.ui.theme.TextRed
import com.example.capocoinapp.ui.theme.TextWhite

@Composable
fun CardComponent (
    cardTitle: String,
    cardSubTitle: String?,
    cardAmount: String?,
    cardSubAmount: String?,
    cardIcon: ImageVector,
    cardTransactionType: String?,
    onClick: () -> Unit = {}
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Icon passed as parameter
            Icon(
                imageVector = cardIcon,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = TextWhite
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cardTitle,
                        style = CapoType.cardTitle

                    )

                    // Sets amount text to green or red depending on income or expense
                    val amountText: Color = when (cardTransactionType){
                        "income" -> TextGreen
                        "expense" -> TextRed
                        else -> TextWhite
                    }

                    if (cardAmount != null) {
                        Text(
                            text = cardAmount,
                            style = CapoType.cardTitle,
                            color = amountText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (cardSubTitle != null) {
                        Text(
                            text = cardSubTitle,
                            style = CapoType.cardSubTitle
                        )
                    }

                    if (cardSubAmount != null) {
                        Text(
                            text = cardSubAmount,
                            style = CapoType.cardSubTitle
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardBox(cards: List<@Composable () -> Unit>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(BackgroundColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        cards.forEach { card ->
            card()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CapoCoinAppTheme {
        CardBox(cards = listOf(
            {
                CardComponent(
                "Dinner Night",
                "Empire Steak",
                "- R200",
                "5:00 PM",
                Icons.Default.Fastfood,
                "expense")},
            {CardComponent(
                    "Movie",
                    "Pavillion",
                    "- R150",
                    "7:45 AM",
                    Icons.Default.Movie,
                    "expense")},
            {CardComponent(
                    "Salary",
                    "Dunder Mifflin",
                    "+ R30 000",
                    "9:45 AM",
                    Icons.Default.Payments,
                    "income")

            }
            )
        )
    }
}