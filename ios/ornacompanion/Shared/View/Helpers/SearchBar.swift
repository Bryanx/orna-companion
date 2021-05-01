//
//  SearchBar.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 01/05/2021.
//

import SwiftUI

struct SearchBar: View {
    @Binding var text: String
 
    @State private var isEditing = false
 
    var body: some View {
        HStack {
            TextField("", text: $text)
                .padding(7)
                .padding(.horizontal, 25)
                .background(ColorUtil.cardColor)
                .cornerRadius(8)
                .foregroundColor(ColorUtil.textColorHeader)
                .overlay(
                    HStack {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(ColorUtil.textColor)
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                            .padding(.leading, 8)
                 
                        if isEditing {
                            Button(action: {
                                self.text = ""
                            }) {
                                Image(systemName: "multiply.circle.fill")
                                    .foregroundColor(ColorUtil.textColor)
                                    .padding(.trailing, 8)
                            }
                        }
                    }
                )
                .padding(.horizontal)
                .onTapGesture {
                    self.isEditing = true
                }
 
            if isEditing {
                Button(action: {
                    self.isEditing = false
                    self.text = ""
 
                }) {
                    Text("Cancel")
                }
                .padding(.trailing)
                .transition(.move(edge: .trailing))
                .animation(.default)
            }
        }
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            ColorUtil.backgroundColorDark
            SearchBar(text: .constant(""))
        }
    }
}
