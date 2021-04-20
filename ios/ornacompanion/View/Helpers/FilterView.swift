//
//  FilterView.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 13/04/2021.
//

import SwiftUI

struct FilterView<Content: View>: View {
    var content: Content
    var bgColor: Color
    var isLoading: Bool
    var title: String
    var onClickFilter: () -> Void
    
    let columns = [GridItem(.flexible()), GridItem(.flexible())]
    
    init(
        _ title: String = "Title",
        bgColor: Color = ColorUtil.backgroundColorDark,
        isLoading: Bool = false,
        onClickFilter: @escaping () -> Void = {},
        content: () -> Content
    ) {
        self.bgColor = bgColor
        self.content = content()
        self.isLoading = isLoading
        self.onClickFilter = onClickFilter
        self.title = title
    }
    
    var body: some View {
        ContainerView(bgColor: bgColor, isLoading: isLoading) {
            ZStack {
                ScrollView {
                    LazyVGrid(columns: columns, spacing: 7) {
                        content
                    }.padding(.horizontal)
                }
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        Fab(onClick: onClickFilter,
                            image: Image(systemName: "line.horizontal.3.decrease"))
                            .padding()
                    }
                }
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
