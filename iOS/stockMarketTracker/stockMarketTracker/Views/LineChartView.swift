//
//  LineChartView.swift
//  stockMarketTracker
//
//  Created by developer on 05/07/2024.
//

import SwiftUI
import Charts

struct LineChartView: View {
    var data: [ChartDataEntry]

    var body: some View {
        Chart {
            ForEach(data, id: \.x) { entry in
                LineMark(
                    x: .value("Date", Date(timeIntervalSince1970: entry.x)),
                    y: .value("Price", entry.y)
                )
            }
        }
        .chartXAxis {
            AxisMarks(values: .automatic(desiredCount: 10))
        }
        .chartYAxis {
            AxisMarks(values: .automatic(desiredCount: 10))
        }
    }
}

struct ChartDataEntry: Identifiable {
    let id = UUID()
    let x: Double
    let y: Double
}

struct LineChartView_Previews: PreviewProvider {
    static var previews: some View {
        LineChartView(data: [
            ChartDataEntry(x: Date().timeIntervalSince1970, y: 100),
            ChartDataEntry(x: Date().addingTimeInterval(86400).timeIntervalSince1970, y: 110),
            ChartDataEntry(x: Date().addingTimeInterval(2 * 86400).timeIntervalSince1970, y: 120)
        ])
    }
}
