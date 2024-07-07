//
//  String+Extensions.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import Foundation

extension String {
    func toDate() -> Date {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.date(from: self) ?? Date()
    }
}

