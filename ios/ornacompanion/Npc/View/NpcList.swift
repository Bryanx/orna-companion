//
//  NpcList.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 02/05/2021.
//

import SwiftUI

struct NpcList: View {
    @StateObject var vm = NpcViewModel()
    @State private var showFilter = false

    var body: some View {
        FilterView(
            "NPCs",
            isLoading: vm.isLoading,
            onClickFilter: { showFilter.toggle() }
        ) {
            ForEach(vm.npcs) { npc in
                NavigationLink(
                    destination: NpcDetail(id: npc.id),
                    label: {
                        NpcListItem(npc)
                    }
                )
            }.animation(.default)
        }
        .sheet(height: .percentage(50.0), isPresented: $showFilter) {
            NpcFilterSheet(vm: vm, showFilter: $showFilter)
        }
        .onAppear(perform: vm.fetchNpcs)
    }
}

struct NpcList_Previews: PreviewProvider {
    static var previews: some View {
        NpcList()
            .previewDevice("iPhone 12")
    }
}
