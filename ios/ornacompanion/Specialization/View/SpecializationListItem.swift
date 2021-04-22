//
//  ItemDetail.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import SwiftUI

struct SpecializationListItem: View {
    var specialization: Specialization
    
    init(_ specialization: Specialization) {
        self.specialization = specialization
    }
    
    var body: some View {
        ZStack(alignment: .center) {
            VStack(alignment:.center) {
                Spacer()
                AsyncImage(url: specialization.getImage(),
                           placeholder: { ProgressView() },
                           image: { Image(uiImage: $0).resizable() })
                    .frame(width: 70, height: 70)
                Text("\(specialization.name)")
                    .fontWeight(.bold)
                    .padding(.top, 2)
                HStack {
                    ForEach(specialization.boosts, id: \.name) { boost in
                        VStack {
                            Text("\(boost.formattedName())")
                            Text("\(boost.formattedValue())")
                        }
                        .foregroundColor(boost.value >= 0 ? .green : .red)
                    }
                }.padding(.vertical, 1)
                Text("\(specialization.formattedCost())")
                    .fontWeight(.bold)
                Spacer()
            }
            VStack {
                Tier(value: specialization.tier)
                Spacer()
            }
        }
        .modifier(CardStyle())
    }
}

struct SpecializationListItem_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationListItem(Specialization.SAMPLE)
        .frame(width: 150, height: 150)
        .previewLayout(.fixed(width: 200, height: 250))
    }
}
