//
//  FilterView.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 13/04/2021.
//

import SwiftUI

struct FilterView<Content: View>: View {
    var content: Content
    var searchText: Binding<String>?
    var bgColor: Color
    var isLoading: Bool
    var title: String
    var onClickFilter: () -> Void
    var columns: [GridItem] = []
    var isShowFilter: Bool
    
    init(
        _ title: String = "Title",
        bgColor: Color = ColorUtil.backgroundColorDark,
        isLoading: Bool = false,
        onClickFilter: @escaping () -> Void = {},
        columns: Int = 2,
        isShowFilter: Bool = true,
        searchText: Binding<String>? = nil,
        content: () -> Content
    ) {
        self.bgColor = bgColor
        self.content = content()
        self.searchText = searchText
        self.isLoading = isLoading
        self.onClickFilter = onClickFilter
        self.title = title
        self.columns = (0..<columns).map { _ in GridItem(.flexible()) }
        self.isShowFilter = isShowFilter
    }
    
    var body: some View {
        ContainerView(bgColor: bgColor, isLoading: isLoading) {
            ZStack {
                VStack {
                    if (self.searchText != nil) {
                        SearchBar(text: searchText ?? .constant(""))
                    }
                    ScrollView {
                        LazyVGrid(columns: columns, spacing: 7) {
                            content
                        }.padding(.horizontal)
                    }.padding(.top, 0.3)
                }
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        Fab(onClick: onClickFilter,
                            image: Image(systemName: "line.horizontal.3.decrease"))
                            .padding()
                    }
                }.hideIf(!isShowFilter)
            }
        }
        .navigationTitle(title)
        .navigationBarTitleDisplayMode(.large)
    }
}

struct FilterView_Previews: PreviewProvider {
    static var previews: some View {
        FilterView {
            Text("test")
        }
    }
}
