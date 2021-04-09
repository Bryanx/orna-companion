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
    
    var body: some View {
        NavigationView {
            ContainerView {
                ScrollView {
                    LazyVGrid(columns: columns, spacing: 7) {
                        ForEach(categories, id: \.self) { key in
                            NavigationLink(
                                destination: SpecializationList(),
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
            }
        }
    }
}

struct Discover_Previews: PreviewProvider {
    static var previews: some View {
        Discover()
            .previewDevice("iPhone 12")
    }
}
