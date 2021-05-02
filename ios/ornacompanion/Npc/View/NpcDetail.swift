//
//  NpcDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 02/05/2021.
//

import SwiftUI
import WrappingHStack

struct NpcDetail: View {
    @StateObject var vm = NpcDetailViewModel()
    @StateObject var searchVm = SearchViewModel()
    var id: Int
    
    var body: some View {
        ContainerView(isLoading: vm.isLoading) {
            ScrollView {
                ZStack(alignment: .top) {
                    VStack {
                        NpcDetailTop(vm: vm)
                        Rectangle().frame(height:1).foregroundColor(ColorUtil.dividerColor)
                        NpcDetailBottom(vm: vm)
                        Spacer()
                    }
                    VStack {
                        Tier(value: vm.npc.tier)
                        SaveButton(save: .constant(Save.of(vm.npc)))
                    }
                    HStack {
                        Text(Constant.NPC.uppercased())
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
            vm.fetchNpc(id: id)
            searchVm.addToSearchHistory(Save.of(vm.npc))
        })
    }
}

struct NpcDetail_Previews: PreviewProvider {
    static var previews: some View {
        NpcDetail(id: 54)
            .previewDevice("iPhone 12")
    }
}

struct NpcDetailTop: View {
    @ObservedObject var vm: NpcDetailViewModel
    
    var body: some View {
        AsyncImage(url: vm.npc.getImage(),
                   placeholder: { ProgressView() },
                   image: { Image(uiImage: $0).resizable() })
            .frame(width: 170, height: 170)
            .padding(.top, 40)
        Text("\(vm.npc.name)")
            .font(.title2)
            .foregroundColor(ColorUtil.textColorHeader)
            .fontWeight(.bold)
            .padding(.top, 2)
        Text(vm.npc.description)
            .padding(.top, 2)
    }
}

struct NpcDetailBottom: View {
    @ObservedObject var vm: NpcDetailViewModel
    
    var body: some View {
        Text("Quests:")
            .padding(.top, 2)
            .hideIf(vm.npc.quests.isEmpty)
        WrappingHStack(vm.npc.quests, alignment: .center, spacing: .constant(0)) { quest in
            VStack {
                HStack(spacing: 2) {
                    Image(systemName: "star.fill")
                        .resizable()
                        .frame(width: 13, height: 13)
                        .foregroundColor(ColorUtil.textColorHeader)
                    Text("\(quest.tier)")
                        .foregroundColor(ColorUtil.textColorHeader)
                        .font(.footnote)
                        .fontWeight(.bold)
                }
                Text(quest.name)
                    .fontWeight(.bold)
            }
            .padding(.all, 20)
            .background(ColorUtil.cardColor)
            .cornerRadius(10)
            .padding(.all, 5)
        }
    }
}
