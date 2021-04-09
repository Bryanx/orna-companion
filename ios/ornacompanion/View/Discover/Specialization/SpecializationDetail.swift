//
//  SpecializationDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import SwiftUI

struct SpecializationDetail: View {
    @StateObject var vm = SpecializationDetailViewModel()
    var id: Int
    
    var body: some View {
        ContainerView(isLoading: vm.isLoading) {
            ZStack(alignment: .topTrailing) {
                HStack {
                    Spacer()
                    VStack {
                        Spacer()
                        AsyncImage(url: vm.specialization.getImage(),
                                   placeholder: { ProgressView() },
                                   image: { Image(uiImage: $0).resizable() })
                            .frame(width: 70, height: 70)
                        Text("\(vm.specialization.name)")
                            .fontWeight(.bold)
                            .padding(.top, 2)
                        HStack {
                            ForEach(vm.specialization.boosts, id: \.name) { boost in
                                VStack {
                                    Text("\(boost.formattedName())")
                                    Text("\(boost.formattedValue())")
                                }
                                .foregroundColor(boost.value >= 0 ? .green : .red)
                            }
                        }.padding(.vertical, 1)
                        Text("\(vm.specialization.cost)")
                            .fontWeight(.bold)
                        Spacer()
                    }
                    Spacer()
                }
                Tier(value: vm.specialization.tier)
            }
        }
        .onAppear(perform: {
            vm.fetchSpecialization(id: id)
        })
    }
}

struct SpecializationDetail_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationDetail(id: 56)
            .previewDevice("iPhone 12")
    }
}
