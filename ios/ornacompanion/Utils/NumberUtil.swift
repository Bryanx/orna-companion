//
//  NumberUtil.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 20/04/2021.
//

import Foundation

struct NumberUtil {
    static func formatNumber(_ value: String) -> String {
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        return formatter.string(from: NSNumber(value: Double(value) ?? 0)) ?? ""
    }
}
