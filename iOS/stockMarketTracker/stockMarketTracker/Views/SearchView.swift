import SwiftUI

struct Stock: Identifiable {
    let id = UUID()
    let symbol: String
    let name: String
}

struct SearchView: View {
    @StateObject private var viewModel = SearchViewModel()
    @State private var searchText = ""
    
    var popularStocks: [Stock] = [
        Stock(symbol: "AAPL", name: "Apple Inc."),
        Stock(symbol: "GOOG", name: "Alphabet Inc."),
        Stock(symbol: "MSFT", name: "Microsoft Corporation"),
        Stock(symbol: "AMZN", name: "Amazon.com, Inc."),
        Stock(symbol: "TSLA", name: "Tesla, Inc."),
        Stock(symbol: "NVDA", name: "NVIDIA Corporation"),
        Stock(symbol: "MCD", name: "McDonald's Corporation"),
        Stock(symbol: "META", name: "Meta Platforms, Inc."),
        Stock(symbol: "NFLX", name: "Netflix, Inc."),
        Stock(symbol: "INTC", name: "Intel Corporation")
    ]
    
    var body: some View {
        NavigationView {
            VStack {
                TextField("Search Stocks", text: $searchText, onCommit: {
                    viewModel.searchStocks(query: searchText)
                })
                .padding()
                .textFieldStyle(RoundedBorderTextFieldStyle())
                
                List(viewModel.searchResults, id: \.symbol) { match in
                    NavigationLink(destination: StockDetailView(symbol: match.symbol)) {
                        VStack(alignment: .leading) {
                            Text(match.name)
                            Text(match.symbol).font(.subheadline).foregroundColor(.gray)
                        }
                    }
                }
                
              
                
                
            }
            .navigationTitle("Stock Search")
            
            .navigationTitle("Stock Search")
            VStack {
                
                List(popularStocks) { stock in
                    NavigationLink(destination: StockDetailView(symbol: stock.symbol)) {
                        VStack(alignment: .leading) {
                            Text(stock.name)
                            Text(stock.symbol).font(.subheadline).foregroundColor(.gray)
                        }
                    }
                }
                
                Spacer()
      
            }.navigationTitle("Popular Stocks")
        }
    }
}

#Preview {
    SearchView()
}
