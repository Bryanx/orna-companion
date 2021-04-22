//
//  DiscoverItem.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 05/04/2021.
//

import SwiftUI

struct DiscoverItem: View {
    var name: String
    var image: String
    
    var body: some View {
        HStack {
            Spacer()
            VStack {
                Spacer()
                Image(image)
                    .renderingMode(.original)
                    .resizable()
                    .frame(width: 60, height: 60)
                Text(name)
                    .foregroundColor(ColorUtil.textColorHeader)
                    .font(.caption)
                    .fontWeight(.bold)
                Spacer()
            }
            Spacer()
        }
        .modifier(CardStyle())
    }
}

struct DiscoverItem_Previews: PreviewProvider {
    static var previews: some View {
        DiscoverItem(
            name: "Classes",
            image: "classes"
        )
        .frame(width: 150, height: 150)
        .previewLayout(.fixed(width: 300, height: 300))
    }
}
