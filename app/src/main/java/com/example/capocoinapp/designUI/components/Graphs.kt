package com.example.capocoinapp.designUI.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.github.mikephil.charting.charts.PieChart
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

                legend.isEnabled = false
                setExtraOffsets(20f, 20f, 20f, 20f) // adds padding so nothing gets clipped
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

    PieChartView(
        data = sampleData,
        colours = sampleColours
    )
}