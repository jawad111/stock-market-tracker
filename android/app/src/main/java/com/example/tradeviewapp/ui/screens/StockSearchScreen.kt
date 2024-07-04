package com.example.tradeviewapp.ui.screens


import com.example.tradeviewapp.viewmodel.StockSearchViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockSearchScreen(onStockClick: (String) -> Unit) {
    val viewModel: StockSearchViewModel = viewModel()
    val query by viewModel.query.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Stock Search") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onQueryChange(it) }, // Update ViewModel
                label = { Text("Search for a stock") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            LazyColumn {
                items(searchResults) { result -> // Use searchResults from ViewModel
                    OutlinedButton(
                        onClick = { onStockClick(result.symbol) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "${result.symbol}: ${result.name}")
                    }
                }
            }
        }
    }
}