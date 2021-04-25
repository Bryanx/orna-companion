//
//  SaveListItem.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 25/04/2021.
//

import SwiftUI

struct SaveListItem: View {
    var save: Save
    
    init(_ save: Save) {
        self.save = save
    }
    
    var body: some View {
        ZStack(alignment: .center) {
            HStack {
                AsyncImage(url: save.image,
                           placeholder: { ProgressView() },
                           image: { Image(uiImage: $0).resizable() })
                    .frame(width: 35, height: 35)
                VStack(alignment: .leading) {
                    HStack {
                        Text(save.name)
                            .fontWeight(.bold)
                            .font(.caption)
                        Text(save.type.uppercased())
                            .font(.system(size:9))
                    }
                    Text("\(save.subText)")
                        .lineLimit(1)
                }
                Spacer()
            }
            VStack {
                Tier(value: save.tier)
                Spacer()
            }
        }
        .multilineTextAlignment(.leading)
        .modifier(CardStyle())
    }
}

struct SaveListItem_Previews: PreviewProvider {
    static var previews: some View {
        SaveListItem(Save(id:0,
                          name: "Cataphract",
                          subText: "An expert in defense who begins battle with Ward activated.",
                          type: Constant.SPECIALIZATION,
                          image: "https://orna.guide/static/orna/img/subclasses/cataphract/default_m.png",
                          tier: 8))
    }
}
