import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tradeviewapp.viewmodel.StockListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockListScreen(onStockClick: (String) -> Unit) {
    val viewModel: StockListViewModel = viewModel()
    val query by viewModel.query.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val commonStockSymbols by viewModel.commonStockSymbols.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Stocks") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onQueryChange(it) }, // Update the ViewModel
                label = { Text("Search for a stock") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            LazyColumn {
                // Use searchResults directly from the ViewModel
                if (searchResults.isNotEmpty()) {
                    items(searchResults) { result ->
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
                } else {
                    items(commonStockSymbols) { stockSymbol ->
                        OutlinedButton(
                            onClick = { onStockClick(stockSymbol) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = stockSymbol)
                        }
                    }
                }
            }
        }
    }
}
