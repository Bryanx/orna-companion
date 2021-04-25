//
//  SkillDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import SwiftUI
import WrappingHStack

struct SkillDetail: View {
    @StateObject var vm = SkillDetailViewModel()
    var id: Int
    
    var body: some View {
        ContainerView(isLoading: vm.isLoading) {
            ScrollView {
                ZStack(alignment: .top) {
                    VStack {
                        SkillDetailTop(vm: vm)
                        Rectangle().frame(height:1).foregroundColor(ColorUtil.dividerColor)
                        SkillDetailBottom(vm: vm)
                        Spacer()
                    }
                    VStack {
                        Tier(value: vm.skill.tier)
                        SaveButton(save: .constant(Save.of(vm.skill)))
                    }
                    HStack {
                        Text(Constant.SKILL.uppercased())
                            .font(.caption)
                        Spacer()
                    }
                }
                .padding()
                .multilineTextAlignment(.center)
                .foregroundColor(ColorUtil.textColor)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .onAppear(perform: {
            vm.fetchSkill(id: id)
        })
    }
}

struct SkillDetail_Previews: PreviewProvider {
    static var previews: some View {
        SkillDetail(id: 54)
            .previewDevice("iPhone 12")
    }
}

struct SkillDetailTop: View {
    @ObservedObject var vm: SkillDetailViewModel
    
    var body: some View {
        AsyncImage(url: vm.skill.getImage(),
                   placeholder: { ProgressView() },
                   image: { Image(uiImage: $0).resizable() })
            .frame(width: 170, height: 170)
            .padding(.top, 40)
        Text("\(vm.skill.name)")
            .font(.title2)
            .foregroundColor(ColorUtil.textColorHeader)
            .fontWeight(.bold)
            .padding(.top, 2)
        Text(vm.skill.formattedTypeAndMana())
            .padding(.top, 2)
        Text(vm.skill.description)
            .padding(.top, 2)
        Text(vm.skill.element)
            .padding(.top, 2)
            .hideIf(vm.skill.element.isEmpty)
        Text(vm.skill.formattedCost())
            .fontWeight(.bold)
            .padding(.top, 2)
            .hideIf(vm.skill.cost == 0)
    }
}

struct SkillDetailBottom: View {
    @ObservedObject var vm: SkillDetailViewModel
    
    var body: some View {
        Text(vm.skill.formattedGives())
            .padding(.top, 2)
            .multilineTextAlignment(.center)
            .hideIf(vm.skill.gives.isEmpty)
        Text(vm.skill.formattedCauses())
            .padding(.top, 2)
            .multilineTextAlignment(.center)
            .hideIf(vm.skill.causes.isEmpty)
        Text("Learned by:")
            .padding(.top, 2)
            .hideIf(vm.skill.learnedBy.isEmpty)
        WrappingHStack(vm.skill.learnedBy, alignment: .center, spacing: .constant(0)) { learnedBy in
            NavigationLink(
                destination: SpecializationDetail(id: learnedBy.id),
                label: {
                    VStack {
                        Text(learnedBy.name)
                            .fontWeight(.bold)
                        Text("Lvl \(learnedBy.level)")
                    }
                    .padding(.all, 20)
                    .background(ColorUtil.cardColor)
                    .cornerRadius(10)
                    .padding(.all, 5)
                }
            )
        }
        Text("Monsters use:")
            .padding(.top, 2)
            .hideIf(vm.skill.monstersUse.isEmpty)
        WrappingHStack(vm.skill.monstersUse, alignment: .center, spacing: .constant(0)) { monstersUse in
            NavigationLink(
                destination: SpecializationDetail(id: monstersUse.id), //TODO
                label: {
                    Text(monstersUse.name)
                        .fontWeight(.bold)
                        .padding(.all, 20)
                        .background(ColorUtil.cardColor)
                        .cornerRadius(10)
                        .padding(.all, 5)
                }
            )
        }
        Text("Pets use:")
            .padding(.top, 2)
            .hideIf(vm.skill.petsUse.isEmpty)
        WrappingHStack(vm.skill.petsUse, alignment: .center, spacing: .constant(0)) { petsUse in
            NavigationLink(
                destination: SpecializationDetail(id: petsUse.id), //TODO
                label: {
                    Text(petsUse.name)
                        .fontWeight(.bold)
                        .padding(.all, 20)
                        .background(ColorUtil.cardColor)
                        .cornerRadius(10)
                        .padding(.all, 5)
                }
            )
        }
    }
}
