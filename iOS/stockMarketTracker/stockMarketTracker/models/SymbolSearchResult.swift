//
//  SymbolSearchResult.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import Foundation

struct SymbolSearchResult: Codable {
    let bestMatches: [Match]
    
    struct Match: Codable {
        let symbol: String
        let name: String
        
        enum CodingKeys: String, CodingKey {
            case symbol = "1. symbol"
            case name = "2. name"
        }
    }
}

