package com.example.tradeviewapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tradeviewapp.data.model.StockDataResult
import com.example.tradeviewapp.data.repository.StockRepository
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tradeviewapp.viewmodel.StockChartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockChartScreen(stockSymbol: String) {
    val viewModel: StockChartViewModel = viewModel()
    val stockDataList by viewModel.stockData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Fetch data when the screen is displayed
    LaunchedEffect(key1 = stockSymbol) {
        viewModel.fetchStockData(stockSymbol, "YOUR_API_KEY")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Chart for $stockSymbol") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                StockChart(stockDataList)
            }
        }
    }
}

@Composable
fun StockChart(stockDataList: List<StockDataResult>) {
    val entries = stockDataList.mapIndexed { index, stockData ->
        Entry(index.toFloat(), stockData.closePrice)
    }
    val labels = stockDataList.map { it.date }

    val lineDataSet = LineDataSet(entries, "Stock Price")
    val lineData = LineData(lineDataSet)

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.granularity = 1f
                xAxis.isGranularityEnabled = true
                data = lineData
                description.isEnabled = false
                legend.isEnabled = false
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = {
            it.data = lineData
            it.notifyDataSetChanged()
            it.invalidate()
        }
    )
}