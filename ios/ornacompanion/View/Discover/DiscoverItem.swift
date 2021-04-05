//
//  DiscoverItem.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 05/04/2021.
//

import SwiftUI

struct DiscoverItem: View {
    var name: String
    var image: String
    
    var body: some View {
        HStack {
            Spacer()
            VStack {
                Spacer()
                Image(image)
                    .renderingMode(.original)
                    .resizable()
                    .frame(width: 50, height: 50)
                    .padding(.top)
                    .padding(.horizontal)
                Text(name)
                    .foregroundColor(.primary)
                    .font(.body)
                    .fontWeight(.bold)
                    .padding(.bottom)
                    .padding(.horizontal)
                Spacer()
            }
            Spacer()
        }
        .background(Color.gray)
        .cornerRadius(10)
    }
}

struct DiscoverItem_Previews: PreviewProvider {
    static var previews: some View {
        DiscoverItem(
            name: "Classes",
            image: "classes"
        )
        .frame(width: 150, height: 150)
        .previewDevice("iPhone 12")
    }
}
