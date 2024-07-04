package com.example.tradeviewapp.data.repository

import com.example.tradeviewapp.data.model.StockDataResult
import com.example.tradeviewapp.data.model.SymbolSearchResult
import com.example.tradeviewapp.data.network.ApiClient
import com.example.tradeviewapp.data.network.ApiInterface
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockRepository {
    private val apiService = ApiClient.retrofit.create(ApiInterface::class.java)

    fun fetchHistoricalData(symbol: String, apiKey: String, callback: (List<StockDataResult>) -> Unit) {
        val call = apiService.getHistoricalData("TIME_SERIES_DAILY", symbol, apiKey)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val jsonObject = response.body()
                    val timeSeries = jsonObject?.getAsJsonObject("Time Series (Daily)")
                    val stockDataList = mutableListOf<StockDataResult>()

                    timeSeries?.entrySet()?.forEach { entry ->
                        val date = entry.key
                        val closePrice = entry.value.asJsonObject.get("4. close").asFloat
                        stockDataList.add(StockDataResult(date, closePrice))
                    }

                    callback(stockDataList)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun searchSymbols(keywords: String, apiKey: String, callback: (List<SymbolSearchResult>) -> Unit) {
        val call = apiService.searchSymbols(keywords = keywords, apiKey = apiKey)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val jsonObject = response.body()
                    val bestMatches = jsonObject?.getAsJsonArray("bestMatches")
                    val searchResults = mutableListOf<SymbolSearchResult>()

                    bestMatches?.forEach { match ->
                        val symbol = match.asJsonObject.get("1. symbol").asString
                        val name = match.asJsonObject.get("2. name").asString
                        searchResults.add(SymbolSearchResult(symbol, name))
                    }

                    callback(searchResults)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}




