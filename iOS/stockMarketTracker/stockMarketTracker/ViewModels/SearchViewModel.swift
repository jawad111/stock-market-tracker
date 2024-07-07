//
//  SearchViewModel.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import Foundation

class SearchViewModel: ObservableObject {
    @Published var searchResults = [SymbolSearchResult.Match]()
    
    private let networkManager = NetworkManager()
    
    func searchStocks(query: String) {
        networkManager.searchStocks(query: query) { result in
            switch result {
            case .success(let matches):
                self.searchResults = matches
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
}

