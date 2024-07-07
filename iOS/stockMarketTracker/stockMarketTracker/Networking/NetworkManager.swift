//
//  NetworkManager.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import Foundation

class NetworkManager: ObservableObject {
    private let apiKey = "Your_API_Key"
    
    func searchStocks(query: String, completion: @escaping (Result<[SymbolSearchResult.Match], Error>) -> Void) {
        let urlString = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=\(query)&apikey=\(apiKey)"
        guard let url = URL(string: urlString) else { return }
        
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let data = data {
                do {
                    let searchResult = try JSONDecoder().decode(SymbolSearchResult.self, from: data)
                    DispatchQueue.main.async {
                        completion(.success(searchResult.bestMatches))
                    }
                } catch {
                    DispatchQueue.main.async {
                        completion(.failure(error))
                    }
                }
            } else if let error = error {
                DispatchQueue.main.async {
                    completion(.failure(error))
                }
            }
        }.resume()
    }
    
    func fetchHistoricalData(symbol: String, completion: @escaping (Result<[StockDataResult], Error>) -> Void) {
            let urlString = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=\(symbol)&apikey=\(apiKey)"
            
            guard let url = URL(string: urlString) else {
                completion(.failure(NetworkError.invalidURL))
                return
            }
            
            URLSession.shared.dataTask(with: url) { data, response, error in
                if let error = error {
                    completion(.failure(error))
                    return
                }
                
                guard let data = data else {
                    completion(.failure(NetworkError.noData))
                    return
                }
                
                do {
                    if let jsonObject = try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any],
                       let timeSeries = jsonObject["Time Series (Daily)"] as? [String: [String: String]] {
                        
                        var stockDataList: [StockDataResult] = []
                        
                        for (date, data) in timeSeries {
                            if let closePriceString = data["4. close"], let closePrice = Float(closePriceString) {
                                let stockData = StockDataResult(date: date, closePrice: closePrice)
                                stockDataList.append(stockData)
                            }
                        }
                        
                        completion(.success(stockDataList))
                    } else {
                        completion(.failure(NetworkError.decodingError))
                    }
                } catch {
                    completion(.failure(error))
                }
            }.resume()
        }
    
    enum NetworkError: Error {
        case invalidURL
        case noData
        case decodingError
    }

}


