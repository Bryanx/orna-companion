//
//  AchievementListItem.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 03/05/2021.
//

import SwiftUI

struct AchievementListItem: View {
    var achievement: Achievement
    
    init(_ achievement: Achievement) {
        self.achievement = achievement
    }
    
    var body: some View {
        ZStack(alignment: .center) {
            VStack(alignment:.center) {
                Spacer()
                HStack {
                    Text("\(achievement.name)")
                        .fontWeight(.bold)
                        .padding(.top, 2)
                    Spacer()
                }
                HStack {
                    Text("\(achievement.requirement)")
                        .padding(.top, 2)
                    Spacer()
                }
                HStack {
                    Text("\(achievement.formattedReward())")
                        .padding(.top, 2)
                    Spacer()
                }
                Spacer()
            }
            VStack {
                Tier(value: achievement.tier)
                Spacer()
            }
        }
        .multilineTextAlignment(.leading)
        .modifier(CardStyle())
    }
}
