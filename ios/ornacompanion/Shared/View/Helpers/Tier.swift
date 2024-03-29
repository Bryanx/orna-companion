//
//  Tier.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 08/04/2021.
//

import SwiftUI

struct Tier: View {
    var value: Int
    
    var body: some View {
        HStack(spacing: 2) {
            Spacer()
            Image(systemName: "star.fill")
                .resizable()
                .frame(width: 13, height: 13)
                .foregroundColor(ColorUtil.textColorHeader)
            Text("\(value)")
                .foregroundColor(ColorUtil.textColorHeader)
                .font(.footnote)
                .fontWeight(.bold)
        }
    }
}

struct Tier_Previews: PreviewProvider {
    static var previews: some View {
        Tier(value: 7)
            .background(ColorUtil.backgroundColorDark)
            .frame(width: 150, height: 150)
            .previewLayout(.fixed(width: 150, height: 150))
            .background(ColorUtil.backgroundColorDark)
    }
}
