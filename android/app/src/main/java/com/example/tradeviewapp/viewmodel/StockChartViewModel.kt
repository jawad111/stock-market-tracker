package com.example.tradeviewapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradeviewapp.data.model.StockDataResult
import com.example.tradeviewapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockChartViewModel : ViewModel() {
    private val stockRepository = StockRepository()

    private val _stockData = MutableStateFlow<List<StockDataResult>>(emptyList())
    val stockData: StateFlow<List<StockDataResult>> = _stockData.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchStockData(stockSymbol: String, apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            stockRepository.fetchHistoricalData(stockSymbol, apiKey) { data ->
                _stockData.value = data
                _isLoading.value = false
            }
        }
    }
}