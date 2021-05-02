//
//  SearchList.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 01/05/2021.
//

import SwiftUI

struct SearchList: View {
    @StateObject var vm = SearchViewModel()

    @ViewBuilder func getDestination(_ save: Save) -> some View {
        switch (save.type) {
        case Constant.SKILL: SkillDetail(id: save.id)
        case Constant.SPECIALIZATION: SpecializationDetail(id: save.id)
        case Constant.NPC: NpcDetail(id: save.id)
        default: SkillDetail(id: save.id)
        }
    }
    
    var body: some View {
        VStack {
            FilterView(
                "Search",
                isLoading: vm.isLoading,
                columns: 1,
                isShowFilter: false,
                searchText: $vm.query
            ) {
                ForEach(vm.searchResults) { save in
                    NavigationLink(
                        destination: getDestination(save),
                        label: {
                            SaveListItem(save)
                        }
                    )
                }.animation(.default)
            }
            .onAppear(perform: vm.fetchSearchHistory)
        }
    }
}

struct SearchList_Previews: PreviewProvider {
    static var previews: some View {
        SearchList()
    }
}
