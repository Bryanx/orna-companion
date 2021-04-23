//
//  ItemDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import SwiftUI

struct SkillListItem: View {
    var skill: Skill
    
    init(_ skill: Skill) {
        self.skill = skill
    }
    
    var body: some View {
        ZStack(alignment: .center) {
            VStack(alignment:.center) {
                Spacer()
                AsyncImage(url: skill.getImage(),
                           placeholder: { ProgressView() },
                           image: { Image(uiImage: $0).resizable() })
                    .frame(width: 70, height: 70)
                Text("\(skill.name)")
                    .fontWeight(.bold)
                    .padding(.top, 2)
                Text("\(skill.formattedTypeAndMana())")
                    .padding(.top, 2)
                Text("\(skill.description)")
                    .padding(.top, 2)
                Text("\(skill.formattedCost())")
                    .fontWeight(.bold)
                    .padding(.top, 2)
                    .hideIf(skill.cost == 0)
                Spacer()
            }
            VStack {
                Tier(value: skill.tier)
                Spacer()
            }
        }
        .multilineTextAlignment(.center)
        .modifier(CardStyle())
    }
}
