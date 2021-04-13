//
//  ItemsList.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 06/04/2021.
//

import SwiftUI

struct SpecializationList: View {
    @StateObject var vm = SpecializationViewModel()
    
    var body: some View {
        FilterView(
            "Specializations",
            isLoading: vm.isLoading,
            onClickFilter: {}
        ) {
            ForEach(vm.specializations) { specialization in
                NavigationLink(
                    destination: SpecializationDetail(id: specialization.id),
                    label: {
                        SpecializationListItem(specialization)
                    }
                )
            }
        }
        .onAppear(perform: vm.fetchSpecializations)
    }
}

struct SpecializationList_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationList()
            .previewDevice("iPhone 12")
    }
}
