package com.example.capocoinapp.designUI.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.RobotoSlab
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PieChartView(data: Map<String, Float>, colours: List<String>) {

    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                description.isEnabled = false

                setUsePercentValues(true)

                isDrawHoleEnabled = true
                holeRadius = 0f
                transparentCircleRadius = 0f

                setDrawEntryLabels(false) // IMPORTANT: prevents label clutter

                legend.isEnabled = true
                legend.orientation = Legend.LegendOrientation.VERTICAL
                legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                legend.textSize = 12f

                setExtraOffsets(20f, 10f, 15f, 10f) // adds padding so nothing gets clipped
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), // IMPORTANT: prevents zoomed-in look
        update = { chart ->

            val entries = data.map { (label, value) ->
                PieEntry(value, label)
            }

            val dataSet = PieDataSet(entries, "").apply {

                this.colors = colours.map { hex ->
                    getColorFromString(hex).toArgb()
                }

                valueTextSize = 12f
                valueTextColor = Color.WHITE
            }

            chart.data = PieData(dataSet)

            chart.setDrawEntryLabels(false) // prevents label overflow
            chart.invalidate()
        }
    )
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
    ){
        chart()
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PieChartPreview() {

    val sampleData = mapOf(
        "Food" to 40f,
        "Transport" to 25f,
        "Bills" to 20f,
        "Savings" to 15f
    )

    val sampleColours = listOf(
        "#FF0000",
        "#0000FF",
        "#00FF00",
        "#FFFF00",
        "#FF00FF"
    )

    CardBox(
        cards = listOf(
            { ChartCard({ PieChartView(sampleData, sampleColours) }) }
        )
    )
}