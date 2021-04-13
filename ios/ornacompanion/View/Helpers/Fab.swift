//
//  FloatingActionButton.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 12/04/2021.
//

import SwiftUI

struct Fab: View {
    let image: Image
    
    var body: some View {
        Circle()
            .shadow(radius: 2, x: 0, y: 2)
            .foregroundColor(ColorUtil.ornaGreen)
            .overlay(image.resizable()
                        .frame(width: 15, height: 15)
                        .foregroundColor(.black))
            .frame(width: 50, height: 50)
    }
}

struct Fab_Previews: PreviewProvider {
    static var previews: some View {
        Fab(image: Image(systemName: "line.horizontal.3.decrease"))
            .frame(width: 75, height: 75)
            .previewLayout(.fixed(width: 75, height: 75))
    }
}
