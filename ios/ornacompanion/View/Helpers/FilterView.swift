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
    
    init(
        _ title: String = "Title",
        bgColor: Color = ColorUtil.backgroundColorDark,
        isLoading: Bool = false,
        onClickFilter: () -> Void = {},
        content: () -> Content
    ) {
        self.bgColor = bgColor
        self.content = content()
        self.isLoading = isLoading
        self.title = title
    }
    
    var body: some View {
        ContainerView(bgColor: bgColor, isLoading: isLoading) {
            ZStack {
                content
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        Fab(image: Image(systemName: "line.horizontal.3.decrease"))
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
