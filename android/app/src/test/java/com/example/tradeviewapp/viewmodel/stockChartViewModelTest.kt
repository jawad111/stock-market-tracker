package com.example.tradeviewapp.viewmodel


import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import com.example.tradeviewapp.data.model.StockDataResult
import com.example.tradeviewapp.data.repository.StockRepository
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.ArgumentMatchers.any

class StockChartViewModelTest : FunSpec({
    test("fetchStockData updates stockData and isLoading states") {
        runTest {
            val mockStockData = listOf(
                StockDataResult("2023-12-01", 150.0f),
                StockDataResult("2023-12-02", 152.5f)
            )
            val mockRepository = mock<StockRepository>()
            val viewModel = StockChartViewModel()

            doAnswer { invocation ->
                val callback = invocation.arguments[2] as (List<StockDataResult>) -> Unit
                callback(mockStockData)
                null
            }.`when`(mockRepository).fetchHistoricalData("AAPL", "YOUR_API_KEY", any())

            viewModel.fetchStockData("AAPL", "YOUR_API_KEY")

            // Check if isLoading is initially true
            viewModel.isLoading.first() shouldBe true

            // Check if stockData is updated with the mock data
            viewModel.stockData.first() shouldBe mockStockData

            // Check if isLoading is set to false after data is fetched
            viewModel.isLoading.first() shouldBe false
        }
    }
})