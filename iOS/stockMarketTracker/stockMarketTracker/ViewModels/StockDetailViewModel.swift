//
//  StockDetailViewModel.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import Foundation
import SwiftUI


class StockDetailViewModel: ObservableObject {
    @Published var chartData = [ChartDataEntry]()
    
    private let networkManager = NetworkManager()
    
    func fetchHistoricalData(for symbol: String) {
        networkManager.fetchHistoricalData(symbol: symbol) { result in
            switch result {
            case .success(let stockDataList):
                self.chartData = stockDataList.compactMap { data in
                    guard let date = data.date.toDate() else { return nil }
                    return ChartDataEntry(x: date.timeIntervalSince1970, y: Double(data.closePrice))
                }.sorted(by: { $0.x < $1.x })
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
}

extension String {
    func toDate() -> Date? {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.date(from: self)
    }
}



