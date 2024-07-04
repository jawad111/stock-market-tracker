package com.example.tradeviewapp

import StockListScreen
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tradeviewapp.data.model.StockDataResult
import com.example.tradeviewapp.data.repository.StockRepository
import com.example.tradeviewapp.ui.screens.StockChartScreen
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockApp()
        }
    }


    @Composable
    fun StockApp() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "stockList") {
            composable("stockList") {
                StockListScreen { stockSymbol ->
                    navController.navigate("stockChart/$stockSymbol")
                }
            }
            composable(
                "stockChart/{stockSymbol}",
                arguments = listOf(navArgument("stockSymbol") { type = NavType.StringType })
            ) { backStackEntry ->
                val stockSymbol = backStackEntry.arguments?.getString("stockSymbol") ?: ""
                StockChartScreen(stockSymbol)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun StockAppPreview() {
        StockApp()
    }
}
