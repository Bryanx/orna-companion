//
//  SpecializationDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import SwiftUI
import WrappingHStack

struct SpecializationDetail: View {
    @StateObject var vm = SpecializationDetailViewModel()
    var id: Int
    
    var body: some View {
        ContainerView(isLoading: vm.isLoading) {
            ScrollView {
                ZStack(alignment: .top) {
                    VStack {
                        SpecializationDetailTop(vm: vm)
                        Rectangle().frame(height:1).foregroundColor(ColorUtil.dividerColor)
                        SpecializationDetailBottom(vm: vm)
                        Spacer()
                    }
                    Tier(value: vm.specialization.tier)
                    HStack {
                        Text("SPECIALIZATION")
                            .font(.caption)
                        Spacer()
                    }
                }
                .padding()
                .foregroundColor(ColorUtil.textColor)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .onAppear(perform: {
            vm.fetchSpecialization(id: id)
        })
    }
}

struct SpecializationDetail_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationDetail(id: 54)
            .previewDevice("iPhone 12")
    }
}

struct SpecializationDetailTop: View {
    @ObservedObject var vm: SpecializationDetailViewModel
    
    var body: some View {
        AsyncImage(url: vm.specialization.getImage(),
                   placeholder: { ProgressView() },
                   image: { Image(uiImage: $0).resizable() })
            .frame(width: 170, height: 170)
            .padding(.top, 40)
        Text("\(vm.specialization.name)")
            .font(.title2)
            .foregroundColor(ColorUtil.textColorHeader)
            .fontWeight(.bold)
            .padding(.top, 2)
        Text(vm.specialization.description)
            .padding(.top, 2)
            .multilineTextAlignment(.center)
        HStack {
            ForEach(vm.specialization.boosts, id: \.name) { boost in
                VStack {
                    Text("\(boost.formattedName())")
                    Text("\(boost.formattedValue())")
                }
                .foregroundColor(boost.value >= 0 ? .green : .red)
            }
        }.padding(.vertical, 1)
        Text("\(vm.specialization.formattedCost())")
            .fontWeight(.bold)
    }
}

struct SpecializationDetailBottom: View {
    @ObservedObject var vm: SpecializationDetailViewModel
    
    var body: some View {
        Text("Additional skill slots: \(vm.specialization.skills.slots)")
            .padding(.top, 2)
            .multilineTextAlignment(.center)
        Text("Passive skills")
            .hideIf(vm.specialization.skills.passives.isEmpty)
        WrappingHStack(vm.specialization.skills.passives, spacing: .dynamic(minSpacing: 8)) { passive in
            VStack {
                Text(passive.name)
                    .padding(.all, 20)
                    .background(ColorUtil.cardColor)
                    .cornerRadius(10)
                    .padding(.all, 5)
            }
        }
        Text("Skills Learned")
            .hideIf(vm.specialization.skills.learns.isEmpty)
        WrappingHStack(vm.specialization.skills.learns, alignment: .center, spacing: .constant(0)) { learn in
            VStack {
                Text(learn.name)
                Text("Lvl \(learn.level)")
            }
            .padding(.all, 20)
            .background(ColorUtil.cardColor)
            .cornerRadius(10)
            .padding(.all, 5)
        }
    }
}
