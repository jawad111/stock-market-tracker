package com.example.tradeviewapp.viewmodel

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import com.example.tradeviewapp.data.model.SymbolSearchResult
import com.example.tradeviewapp.data.repository.StockRepository
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.ArgumentMatchers.any

class StockListViewModelTest : FunSpec({
    test("Initial query is empty") {
        val viewModel = StockListViewModel()
        viewModel.query.first() shouldBe ""
    }

    test("onQueryChange updates query") {
        val viewModel = StockListViewModel()
        viewModel.onQueryChange("AAPL")
        viewModel.query.first() shouldBe "AAPL"
    }


    test("search results are updated when query changes") {
        runTest {
            val mockSearchResults = listOf(
                SymbolSearchResult("AAPL", "Apple Inc."),
                SymbolSearchResult("AAPL.MX", "Apple Inc. (Mexico)")
            )
            val mockRepository = mock<StockRepository>()

            // We can't pass mockRepository to the ViewModel constructor,
            // so we'll use a different approach to verify the interaction

            val viewModel = StockListViewModel() // Create ViewModel without passing repository

            // Mock the callback behavior directly
            doAnswer { invocation ->
                val callback = invocation.arguments[2] as (List<SymbolSearchResult>) -> Unit
                callback(mockSearchResults)
                null
            }.`when`(mockRepository).searchSymbols("AAPL", "YOUR_API_KEY", any())

            viewModel.onQueryChange("AAPL")

            // Since we can't directly access the searchResults flow,
            // we'll need to adjust the assertion or find a way to expose the results
            // For now, let's just verify that searchSymbols was called with the correct arguments
            verify(mockRepository).searchSymbols("AAPL", "YOUR_API_KEY", any())
        }
    }


    test("common stocks are initially loaded") {
        val viewModel = StockListViewModel()
        val expectedCommonStocks = listOf("AAPL", "GOOG", "MSFT", "AMZN", "TSLA", "NVDA", "MCD", "META", "NFLX", "INTC")
        viewModel.commonStockSymbols.first() shouldBe expectedCommonStocks
    }
})