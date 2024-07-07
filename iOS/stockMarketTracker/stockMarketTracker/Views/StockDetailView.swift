//
//  StockDetailView.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import SwiftUI
import Charts

struct StockDetailView: View {
    @StateObject private var viewModel = StockDetailViewModel()
    var symbol: String
    
    var body: some View {
        VStack {
            if viewModel.chartData.isEmpty {
                Text("Loading...")
                    .onAppear {
                        viewModel.fetchHistoricalData(for: symbol)
                    }
            } else {
                LineChartView(data: viewModel.chartData)
                    .navigationTitle(symbol)
            }
        }
    }
}


