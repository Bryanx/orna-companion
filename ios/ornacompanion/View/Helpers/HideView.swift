//
//  HideView.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 11/04/2021.
//

import Foundation
import SwiftUI

extension View {
    @ViewBuilder func hideIf(_ shouldHide: Bool) -> some View {
        switch shouldHide {
        case true: self.hidden()
        case false: self
        }
    }
}
