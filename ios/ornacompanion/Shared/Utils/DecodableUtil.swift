//
//  DecodableUtil.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 23/04/2021.
//

import Foundation
import BetterCodable

struct DefToZero: DefaultCodableStrategy {
  static var defaultValue: Int { 0 }
}

struct DefToBlank: DefaultCodableStrategy {
  static var defaultValue: String { "" }
}

typealias DefaultToZero = DefaultCodable<DefToZero>
typealias DefaultToBlank = DefaultCodable<DefToBlank>
