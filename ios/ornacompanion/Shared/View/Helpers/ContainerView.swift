//
//  BgView.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 06/04/2021.
//

import SwiftUI

struct ContainerView<Content>: View where Content : View {
    
    var content: Content
    var bgColor: Color
    var isLoading: Bool
    
    init(
        bgColor: Color = ColorUtil.backgroundColorDark,
        isLoading: Bool = false,
        content: () -> Content
    ) {
        self.bgColor = bgColor
        self.content = content()
        self.isLoading = isLoading
        UITabBar.appearance().barTintColor = UIColor(ColorUtil.backgroundColorMenu)
        UINavigationBar.appearance().barTintColor = UIColor(bgColor)
        UINavigationBar.appearance().backgroundColor = UIColor(bgColor)
        UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor(ColorUtil.textColorHeader)]
        UINavigationBar.appearance().titleTextAttributes = [.foregroundColor: UIColor(ColorUtil.textColorHeader)]
    }
    
    var body : some View {
        if isLoading {
            ZStack {
                bgColor.edgesIgnoringSafeArea(.all)
                ProgressView()
            }
        } else {
            ZStack {
                bgColor.edgesIgnoringSafeArea(.all)
                content
            }
        }
    }
}

struct ContainerView_Previews: PreviewProvider {
    static var previews: some View {
        ContainerView(bgColor: ColorUtil.ornaGreen, isLoading: true) {
            Text("content")
        }
        .previewDevice("iPhone 12")
    }
}
