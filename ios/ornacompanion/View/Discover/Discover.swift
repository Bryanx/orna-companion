//
//  DiscoverView.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 05/04/2021.
//

import SwiftUI

struct Discover: View {
    let categories: [String: String] = [
        "Classes": "classes",
        "Specializations": "specializations",
        "Achievements": "achievements",
        "NPCs": "npcs",
        "Pets": "pets",
        "Skills": "skills",
        "Monsters": "monsters",
        "Items": "items"
    ]
    
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    var body: some View {
        NavigationView {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 20) {
                    ForEach(Array(categories.keys), id: \.self) { key in
                        DiscoverItem(
                            name: key,
                            image:String(categories[key] ?? "")
                        )
                    }
                }
                .padding(.horizontal)
            }.navigationTitle("Orna Companion")
        }
    }
}

struct Discover_Previews: PreviewProvider {
    static var previews: some View {
        Discover()
            .previewDevice("iPhone 12")
    }
}
