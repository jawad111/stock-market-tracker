package com.example.tradeviewapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradeviewapp.data.model.SymbolSearchResult
import com.example.tradeviewapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockListViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SymbolSearchResult>>(emptyList())
    val searchResults: StateFlow<List<SymbolSearchResult>> = _searchResults.asStateFlow()

    private val stockRepository = StockRepository()

    private val _commonStockSymbols = MutableStateFlow(
        listOf("AAPL", "GOOG", "MSFT", "AMZN", "TSLA", "NVDA", "MCD", "META", "NFLX", "INTC")
    )

    val commonStockSymbols: StateFlow<List<String>> = _commonStockSymbols.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    init {
        viewModelScope.launch {
            query.collect { query ->
                if (query.isNotEmpty()) {
                    stockRepository.searchSymbols(query, "YOUR_API_KEY") { results ->
                        // Update the value of _searchResults
                        _searchResults.value = results
                    }
                } else {
                    _searchResults.value = emptyList()
                }
            }
        }
    }
}