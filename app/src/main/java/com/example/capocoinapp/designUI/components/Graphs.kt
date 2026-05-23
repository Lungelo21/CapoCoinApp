package com.example.capocoinapp.designUI.components

import android.R.attr.data
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.GroupBar
import co.yml.charts.ui.barchart.models.GroupBarChartData
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.capocoinapp.ui.theme.CapoType
import com.example.capocoinapp.ui.theme.CardBG
import com.example.capocoinapp.ui.theme.Primary
import kotlin.collections.getOrNull
import kotlin.collections.mapIndexed
import co.yml.charts.common.model.Point
import com.example.capocoinapp.ui.theme.Accent
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.columnSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import kotlinx.coroutines.runBlocking


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
fun ComposeBarChart(data: List<Double>, xLabels: List<String>) {

    // Instantiate modelProducer
    val modelProducer = remember { CartesianChartModelProducer() }

    // Bar styling
    val columnLayer = rememberColumnCartesianLayer(
        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
            rememberLineComponent(
                fill = Fill(Accent),
                thickness = 16.dp
            )
        )
    )

    // Render bars
    LaunchedEffect(data) {
        modelProducer.runTransaction {
            columnSeries {
                series(data)
            }
        }
    }
    CartesianChartHost(
        chart = rememberCartesianChart(
            columnLayer,
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = { _, value, _ ->
                    xLabels.getOrNull(value.toInt()) ?: ""
                }
            )
        ),
        modelProducer = modelProducer
    )
}

@Composable
fun PieChartTypeToggle(
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
fun AnalyticsChartToggle(
    selectedChart: String,
    onChartSelected: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = { onChartSelected("Totals") },
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (selectedChart == "Totals")
                        Primary
                    else
                        CardBG
            )
        ) {
            Text(
                text = "Totals",
                style = CapoType.cardTitle
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Button(
            onClick = { onChartSelected("Budget") },
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (selectedChart == "Budget")
                        Primary
                    else
                        CardBG
            )
        ) {
            Text(
                text = "Budgets",
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
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            chart()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryChartPreview() {

    val sampleSlices = listOf(
        PieChartData.Slice(
            label = "Food",
            value = 40f,
            color = Color.Red
        ),
        PieChartData.Slice(
            label = "Transport",
            value = 25f,
            color = Color.Blue
        ),
        PieChartData.Slice(
            label = "Entertainment",
            value = 20f,
            color = Color.Green
        ),
        PieChartData.Slice(
            label = "Savings",
            value = 15f,
            color = Color.Magenta
        )
    )

    CardBox(
        cards = listOf(
//            { PieChartTypeToggle("Expense", {}) },
//            { AnalyticsChartToggle("Budget", {}) },
//            { ChartCard({ CategoryPieChart(sampleSlices) }) },
//            { ChartCard({ ExpenseBarChart(sampleBarData) }) }
            {
                ComposeBarChart(
                    data = listOf(5.2, 6.1, 3.0, 8.4, 2.5),
                    xLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri"),
                )
            }
        )
    )
}