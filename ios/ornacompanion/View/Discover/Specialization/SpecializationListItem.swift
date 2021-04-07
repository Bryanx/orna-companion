//
//  ItemDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import SwiftUI

struct SpecializationListItem: View {
    var name: String
    var image: String
    
    var body: some View {
        HStack {
            Spacer()
            VStack {
                Spacer()
                AsyncImage(url: URL(string: image)!,
                           placeholder: { Text("Loading ...") },
                           image: { Image(uiImage: $0).resizable() })
                    .frame(width: 60, height: 60)
                Text(name)
                    .foregroundColor(ColorUtil.textColorHeader)
                    .font(.body)
                    .fontWeight(.bold)
                Spacer()
            }
            Spacer()
        }
        .modifier(CardStyle())
    }
}

struct SpecializationListItem_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationListItem(
            name: "Classes",
            image: "https://orna.guide/static/orna/img/subclasses/apprentice/default_m.png"
        )
        .frame(width: 150, height: 150)
        .previewDevice("iPhone 12")
    }
}
