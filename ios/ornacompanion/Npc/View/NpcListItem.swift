//
//  NpcListItem.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 02/05/2021.
//

import SwiftUI

struct NpcListItem: View {
    var npc: Npc
    
    init(_ npc: Npc) {
        self.npc = npc
    }
    
    var body: some View {
        ZStack(alignment: .center) {
            VStack(alignment:.center) {
                Spacer()
                AsyncImage(url: npc.getImage(),
                           placeholder: { ProgressView() },
                           image: { Image(uiImage: $0).resizable() })
                    .frame(width: 70, height: 70)
                Text("\(npc.name)")
                    .fontWeight(.bold)
                    .padding(.top, 2)
                Spacer()
            }
            VStack {
                Tier(value: npc.tier)
                Spacer()
            }
        }
        .multilineTextAlignment(.center)
        .modifier(CardStyle())
    }
}
