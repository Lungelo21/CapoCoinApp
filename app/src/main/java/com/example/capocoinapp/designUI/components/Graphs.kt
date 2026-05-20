package com.example.capocoinapp.designUI.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.capocoinapp.ui.theme.CapoCoinAppTheme
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun PieChartView(data: Map<String, Float>) {

    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
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
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .height(300.dp), // IMPORTANT: prevents zoomed-in look
        update = { chart ->

            val entries = data.map { (label, value) ->
                PieEntry(value, label)
            }

            val dataSet = PieDataSet(entries, "").apply {
                colors = listOf(
                    android.graphics.Color.RED,
                    android.graphics.Color.BLUE,
                    android.graphics.Color.GREEN,
                    android.graphics.Color.YELLOW,
                    android.graphics.Color.MAGENTA
                )

                valueTextSize = 12f
                valueTextColor = android.graphics.Color.WHITE
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

    PieChartView(data = sampleData)
}