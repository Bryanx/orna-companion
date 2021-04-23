//
//  ItemsList.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 06/04/2021.
//

import SwiftUI

struct SkillList: View {
    @StateObject var vm = SkillViewModel()
    @State private var showFilter = false

    var body: some View {
        FilterView(
            "Skills",
            isLoading: vm.isLoading,
            onClickFilter: { showFilter.toggle() }
        ) {
            ForEach(vm.skills) { skill in
                NavigationLink(
                    destination: SkillDetail(id: skill.id),
                    label: {
                        SkillListItem(skill)
                    }
                )
            }.animation(.default)
        }
        .sheet(height: .percentage(50.0), isPresented: $showFilter) {
            SkillFilterSheet(vm: vm, showFilter: $showFilter)
        }
        .onAppear(perform: vm.fetchSkills)
    }
}

struct SkillList_Previews: PreviewProvider {
    static var previews: some View {
        SkillList()
            .previewDevice("iPhone 12")
    }
}
