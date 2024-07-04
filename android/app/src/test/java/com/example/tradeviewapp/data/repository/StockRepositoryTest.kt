package com.example.tradeviewapp.data.repository

import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import com.example.tradeviewapp.data.model.StockDataResult
import com.example.tradeviewapp.data.model.SymbolSearchResult
import com.example.tradeviewapp.data.network.ApiInterface
import com.google.gson.JsonObject
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.mockito.ArgumentMatchers.any

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockRepositoryTest : FunSpec({
    val mockApiService = mock<ApiInterface>()
    val repository = StockRepository() // Pass the mock ApiService

    test("fetchHistoricalData parses response correctly") {
        runTest {
            val mockResponse = Response.success(
                JsonObject().apply {
                    add(
                        "Time Series (Daily)", JsonObject().apply {
                            add(
                                "2023-12-01", JsonObject().apply {
                                    addProperty("4. close", "150.0")
                                }
                            )
                            add(
                                "2023-12-02", JsonObject().apply {
                                    addProperty("4. close", "152.5")
                                }
                            )
                        }
                    )
                }
            )

            doAnswer { invocation ->
                val callback = invocation.arguments[0] as Callback<JsonObject>
                callback.onResponse(mock<Call<JsonObject>>(), mockResponse)
                null
            }.`when`(mockApiService).getHistoricalData(any(), any(), any())

            var result: List<StockDataResult>? = null
            repository.fetchHistoricalData("AAPL", "YOUR_API_KEY") { data ->
                result = data
            }

            result shouldBe listOf(
                StockDataResult("2023-12-01", 150.0f),
                StockDataResult("2023-12-02", 152.5f)
            )
        }
    }

    test("searchSymbols parses response correctly") {
        runTest {
            val mockResponse = Response.success(
                JsonObject().apply {
                    add(
                        "bestMatches", JsonObject().apply {
                            add(
                                "0", JsonObject().apply {
                                    addProperty("1. symbol", "AAPL")
                                    addProperty("2. name", "Apple Inc.")
                                }
                            )
                            add(
                                "1", JsonObject().apply {
                                    addProperty("1. symbol", "MSFT")
                                    addProperty("2. name", "Microsoft Corporation")
                                }
                            )
                        }
                    )
                }
            )

            doAnswer { invocation ->
                val callback = invocation.arguments[0] as Callback<JsonObject>
                callback.onResponse(mock<Call<JsonObject>>(), mockResponse)
                null
            }.`when`(mockApiService).searchSymbols(any(), any(), any())

            var result: List<SymbolSearchResult>? = null
            repository.searchSymbols("AAPL", "YOUR_API_KEY") { data ->
                result = data
            }

            result shouldBe listOf(
                SymbolSearchResult("AAPL", "Apple Inc."),
                SymbolSearchResult("MSFT", "Microsoft Corporation")
            )
        }
    }
})