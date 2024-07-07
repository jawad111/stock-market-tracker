//
//  TimeSeriesWeekly.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import Foundation

struct StockDataResult: Identifiable, Codable {
    let id = UUID()
    let date: String
    let closePrice: Float
}


