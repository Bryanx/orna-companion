//
//  CardStyle.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 06/04/2021.
//

import SwiftUI

struct CardStyle: ViewModifier {
    func body(content: Content) -> some View {
        content
            .padding()
            .background(ColorUtil.cardColor)
            .foregroundColor(Color.white)
            .font(.largeTitle)
            .cornerRadius(10)
            .shadow(radius: 3)
    }
}
