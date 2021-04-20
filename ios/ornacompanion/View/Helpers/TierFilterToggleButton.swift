//
//  TierFilterToggleButton.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 14/04/2021.
//

import SwiftUI

struct TierFilterToggleButton: View {
    var tier: Int
    var onClick: () -> Void
    var isSelected = false
    
    init(
        _ tier: Int,
        onClick: @escaping () -> Void,
        isSelected: Bool
    ) {
        self.tier = tier
        self.onClick = onClick
        self.isSelected = isSelected
    }
    
    var body: some View {
        Button(action: self.onClick) {
            HStack {
                Image(systemName: "star.fill")
                    .resizable()
                    .frame(width: 15, height: 15)
                    .foregroundColor(isSelected ? .black : ColorUtil.textColorHeader)
                Text("\(tier)")
                    .fontWeight(.bold)
                    .foregroundColor(isSelected ? .black : ColorUtil.textColorHeader)
            }
            .padding(.all, 20)
            .background(isSelected ? ColorUtil.ornaGreen : ColorUtil.cardColor)
            .cornerRadius(10)
            .padding(.all, 5)
        }
    }
}

struct TierFilterToggleButton_Previews: PreviewProvider {
    static var previews: some View {
        TierFilterToggleButton(0, onClick: {}, isSelected: false)
    }
}
