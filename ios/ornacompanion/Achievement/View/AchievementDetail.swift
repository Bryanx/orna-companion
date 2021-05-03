//
//  AchievementDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 03/05/2021.
//

import SwiftUI
import WrappingHStack

struct AchievementDetail: View {
    @StateObject var vm = AchievementDetailViewModel()
    @StateObject var searchVm = SearchViewModel()
    var id: Int
    
    var body: some View {
        ContainerView(isLoading: vm.isLoading) {
            ScrollView {
                ZStack(alignment: .top) {
                    VStack {
                        AchievementDetailTop(vm: vm)
                        Spacer()
                    }
                    VStack {
                        Tier(value: vm.achievement.tier)
                        SaveButton(save: .constant(Save.of(vm.achievement)))
                    }
                    HStack {
                        Text(Constant.ACHIEVEMENT.uppercased())
                            .font(.caption)
                        Spacer()
                    }
                }
                .padding()
                .multilineTextAlignment(.center)
                .foregroundColor(ColorUtil.textColor)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .onAppear(perform: {
            vm.fetchAchievement(id: id)
            searchVm.addToSearchHistory(Save.of(vm.achievement))
        })
    }
}

struct AchievementDetail_Previews: PreviewProvider {
    static var previews: some View {
        AchievementDetail(id: 54)
            .previewDevice("iPhone 12")
    }
}

struct AchievementDetailTop: View {
    @ObservedObject var vm: AchievementDetailViewModel
    
    var body: some View {
        Text("\(vm.achievement.name)")
            .font(.title2)
            .foregroundColor(ColorUtil.textColorHeader)
            .fontWeight(.bold)
            .padding(.top, 100)
        Text("\(vm.achievement.requirement)")
            .padding(.top, 2)
        Text("\(vm.achievement.formattedReward())")
            .padding(.top, 2)
    }
}
