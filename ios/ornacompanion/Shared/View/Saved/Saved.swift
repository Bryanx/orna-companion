//
//  Saved.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 05/04/2021.
//

import SwiftUI

struct Saved: View {
    @StateObject var vm = SaveViewModel()
    @State private var showFilter = false

    @ViewBuilder func getDestination(_ save: Save) -> some View {
        switch (save.type) {
        case Constant.SKILL: SkillDetail(id: save.id)
        case Constant.SPECIALIZATION: SpecializationDetail(id: save.id)
        default: SkillDetail(id: save.id)
        }
    }
    
    var body: some View {
        FilterView(
            "Saved",
            isLoading: vm.isLoading,
            onClickFilter: { showFilter.toggle() },
            columns: 1,
            isShowFilter: false
        ) {
            ForEach(vm.saves) { save in
                NavigationLink(
                    destination: getDestination(save),
                    label: {
                        SaveListItem(save)
                    }
                )
            }.animation(.default)
        }
        .onAppear(perform: vm.fetchSaves)
    }
}

struct Saved_Previews: PreviewProvider {
    static var previews: some View {
        Saved()
            .previewDevice("iPhone 12")
    }
}
