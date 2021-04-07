//
//  ContentView.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 05/04/2021.
//

import SwiftUI

struct ContentView: View {
    @State private var selection: Tab = .discover
    
    enum Tab {
        case discover
        case saved
        case search
        case guides
        case settings
    }
    
    var body: some View {
        ContainerView {
            TabView(selection: $selection) {
                Discover()
                    .tabItem {
                        Label("Discover", systemImage: "safari")
                    }
                    .tag(Tab.discover)
                Saved()
                    .tabItem {
                        Label("Saved", systemImage: "bookmark")
                    }
                    .tag(Tab.saved)
                Search()
                    .tabItem {
                        Label("Search", systemImage: "magnifyingglass")
                    }
                    .tag(Tab.search)
                Guides()
                    .tabItem {
                        Label("Guides", systemImage: "book")
                    }
                    .tag(Tab.guides)
                Settings()
                    .tabItem {
                        Label("Settings", systemImage: "gear")
                    }
                    .tag(Tab.settings)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .previewDevice("iPhone 12")
            .environmentObject(SpecializationViewModel())
    }
}

