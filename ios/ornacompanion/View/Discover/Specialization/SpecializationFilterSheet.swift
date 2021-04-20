//
//  SpecializationFilterSheet.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 13/04/2021.
//

import SwiftUI
import WrappingHStack

struct SpecializationFilterSheet: View {
    @State var selectedTiers = [1]
    @ObservedObject var vm: SpecializationViewModel
    @Binding var showFilter: Bool
    
    fileprivate func isSelected(_ tier: Int) -> Bool {
        return selectedTiers.contains(tier)
    }
    
    func onClickTier(_ tier: Int) {
        if (selectedTiers.contains(tier)) {
            self.selectedTiers = self.selectedTiers.filter { $0 != tier }
        } else {
            self.selectedTiers += [tier]
        }
    }
    
    var body: some View {
        ContainerView {
            VStack(alignment: .leading) {
                HStack(alignment: .top) {
                    Text("Tier")
                        .font(.title2)
                    Spacer()
                }.padding(.horizontal, 5.0)
                WrappingHStack(vm.allPossibleTiers, spacing: .dynamic(minSpacing: 0)) { tier in
                    TierFilterToggleButton(tier, onClick: {onClickTier(tier)}, isSelected: isSelected(tier))
                }
                Button(action: {
                    vm.applyFilter(selectedTiers)
                    showFilter = false
                }) {
                    HStack {
                        Spacer()
                        Text("APPLY FILTER")
                            .foregroundColor(.black).padding()
                        Spacer()
                    }.background(ColorUtil.ornaGreen)
                    .cornerRadius(10)
                }.padding(.horizontal, 5.0)
            }
            .padding()
        }
        .foregroundColor(ColorUtil.textColor)
        .onAppear {
            vm.fetchAllPossibleTiers()
            selectedTiers = vm.selectedTiers
        }
    }
}

struct SpecializationFilterSheet_Previews: PreviewProvider {
    static var previews: some View {
        SpecializationFilterSheet(vm: SpecializationViewModel(), showFilter: .constant(true))
    }
}
