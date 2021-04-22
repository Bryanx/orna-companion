//
//  ArrayUtil.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 15/04/2021.
//

import Foundation

extension Sequence where Element: Hashable {
    func distinct() -> [Element] {
        var set = Set<Element>()
        return filter { set.insert($0).inserted }
    }
}
