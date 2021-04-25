//
//  SaveButton.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 25/04/2021.
//

import SwiftUI

struct SaveButton: View {
    @StateObject var vm = SaveViewModel()
    @Binding var save: Save
    
    var body: some View {
        Button(action: { vm.toggleSave(save) },
               label: {
                HStack(spacing: 3) {
                    Spacer()
                    Image(systemName: vm.isSaved ? "bookmark.fill" : "bookmark")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 13)
                        .foregroundColor(ColorUtil.textColorHeader)
                }
                .padding(.top, 5)
                .padding(.trailing, 5)
                
               }
        )
        .onChange(of: save) { updatedSave in
            vm.fetchIsSaved(updatedSave)
        }
        .onAppear {
            vm.fetchIsSaved(save)
        }
    }
}

struct SaveButton_Previews: PreviewProvider {
    static var previews: some View {
        SaveButton(save: .constant(Save(id:0,
                              name: "Cataphract",
                              subText: "An expert in defense who begins battle with Ward activated.",
                              type: Constant.SPECIALIZATION,
                              image: "https://orna.guide/static/orna/img/subclasses/cataphract/default_m.png",
                              tier: 8)))
            .frame(width: 20, height: 20)
            .previewLayout(.fixed(width: 150, height: 150))
    }
}
