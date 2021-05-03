//
//  AchievementList.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 03/05/2021.
//

import SwiftUI

struct AchievementList: View {
    @StateObject var vm = AchievementViewModel()
    @State private var showFilter = false

    var body: some View {
        FilterView(
            "Achievements",
            isLoading: vm.isLoading,
            onClickFilter: { showFilter.toggle() }
        ) {
            ForEach(vm.achievements) { achievement in
                NavigationLink(
                    destination: AchievementDetail(id: achievement.id),
                    label: {
                        AchievementListItem(achievement)
                    }
                )
            }.animation(.default)
        }
        .sheet(height: .percentage(50.0), isPresented: $showFilter) {
            AchievementFilterSheet(vm: vm, showFilter: $showFilter)
        }
        .onAppear(perform: vm.fetchAchievements)
    }
}

struct AchievementList_Previews: PreviewProvider {
    static var previews: some View {
        AchievementList()
            .previewDevice("iPhone 12")
    }
}
