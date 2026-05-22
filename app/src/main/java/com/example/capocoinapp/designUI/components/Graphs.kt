package com.example.capocoinapp.designUI.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.graphics.Color
import android.view.ViewGroup
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.Primary
import com.example.capocoinapp.ui.theme.RobotoSlab

@Composable
fun CategoryPieChart(slices: List<PieChartData.Slice>) {

    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = true,
        backgroundColor = CardBG,
    )

    PieChart(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig
    )
}

@Composable
fun ChartTypeToggle(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                onTypeSelected("Expense")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (selectedType == "Expense")
                        Primary
                    else
                        CardBG
            )
        ) {
            Text(
                text = "Expenses",
                style = CapoType.cardTitle
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = {
                onTypeSelected("Income")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (selectedType == "Income")
                        Primary
                    else
                        CardBG
            )
        ) {
            Text(
                text = "Income",
                style = CapoType.cardTitle
            )
        }
    }
}

@Composable
fun ChartCard(chart: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBG
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            chart()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryPieChartPreview() {

    val sampleSlices = listOf(
        PieChartData.Slice(
            label = "Food",
            value = 40f,
            color = androidx.compose.ui.graphics.Color.Red
        ),
        PieChartData.Slice(
            label = "Transport",
            value = 25f,
            color = androidx.compose.ui.graphics.Color.Blue
        ),
        PieChartData.Slice(
            label = "Entertainment",
            value = 20f,
            color = androidx.compose.ui.graphics.Color.Green
        ),
        PieChartData.Slice(
            label = "Savings",
            value = 15f,
            color = androidx.compose.ui.graphics.Color.Magenta
        )
    )

    CardBox(
        cards = listOf(
            { ChartTypeToggle("Expense", {}) },
            { ChartCard({ CategoryPieChart(sampleSlices) }) }
        )
    )
}