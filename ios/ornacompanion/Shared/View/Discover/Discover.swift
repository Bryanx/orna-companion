//
//  DiscoverView.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 05/04/2021.
//

import SwiftUI

struct Discover: View {

    let categories: [String] = ["Classes","Specializations","Achievements","NPCs","Pets","Skills","Monsters","Items"]
    
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    @ViewBuilder func getDestination(_ key: String) -> some View {
        if key == categories[1] {
            SpecializationList()
        } else {
            SkillList()
        }
    }
    
    var body: some View {
        ContainerView {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 7) {
                    ForEach(categories, id: \.self) { key in
                        NavigationLink(
                            destination: getDestination(key),
                            label: {
                                DiscoverItem(
                                    name: key,
                                    image: key.lowercased()
                                )
                            }
                        )
                    }
                }.padding(.horizontal)
            }.navigationTitle("Orna Companion")
            .padding(.top, 0.3)
        }
    }
}

struct Discover_Previews: PreviewProvider {
    static var previews: some View {
        Discover()
            .previewDevice("iPhone 12")
    }
}
