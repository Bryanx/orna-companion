//
//  ItemsList.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 06/04/2021.
//

import SwiftUI

struct SpecializationList: View {
    @StateObject var vm = SpecializationViewModel()
    
    let columns = [GridItem(.flexible()), GridItem(.flexible())]
    
    var body: some View {
        ContainerView(isLoading: vm.isLoading) {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 7) {
                    ForEach(vm.specializations) { item in
                        NavigationLink(
                            destination: SpecializationDetail(id: item.id),
                            label: {
                                SpecializationListItem(
                                    specialization: item)
                            }
                        )
                    }
                }.padding(.horizontal)
            }
        }
        .navigationTitle("Specializations")
        .navigationBarTitleDisplayMode(.large)
        .onAppear(perform: vm.fetchSpecializations)
    }
}

struct SpecializationList_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationList()
            .previewDevice("iPhone 12")
    }
}
