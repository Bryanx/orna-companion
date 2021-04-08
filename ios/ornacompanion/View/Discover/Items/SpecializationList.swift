//
//  ItemsList.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 06/04/2021.
//

import SwiftUI

struct SpecializationList: View {
    @EnvironmentObject var specializationVM: SpecializationViewModel
    
    let columns = [GridItem(.flexible()), GridItem(.flexible())]
    
    var body: some View {
        ContainerView {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 7) {
                    ForEach(specializationVM.specializations) { item in
                        NavigationLink(
                            destination: SpecializationDetail(),
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
        .onAppear {
            specializationVM.fetchSpecialization()
        }
    }
}

struct SpecializationList_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationList()
            .previewDevice("iPhone 12")
            .environmentObject(SpecializationViewModel())
    }
}
