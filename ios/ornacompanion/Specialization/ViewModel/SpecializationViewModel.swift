//
//  SpecializationViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import Foundation
import SwiftUI

public class SpecializationViewModel: ObservableObject {
    @Published var specializations = [Specialization]()
    @Published var allPossibleTiers = [Int]()
    @Published var selectedTiers = [3]
    @Published var isLoading = true
    
    func fetchAllPossibleTiers() {
        guard self.allPossibleTiers.isEmpty else { return }
        let result: [Specialization] = FileUtil.read(Constant.DB_SPECIALIZATION_NAME) ?? []
        self.allPossibleTiers = result.map { $0.tier }.distinct().sorted()
    }
    
    func applyFilter(_ selectedTiers: [Int]) {
        self.selectedTiers = selectedTiers
        fetchSpecializations()
    }
    
    func fetchSpecializations() {
        let result: [Specialization] = FileUtil.read(Constant.DB_SPECIALIZATION_NAME) ?? []
        if !result.isEmpty {
            self.specializations = result
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            isLoading = false
            return
        }
        
        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }
        
        IoUtil.loadFromNetwork(url, onSuccess: { (results:[Specialization]) in
            self.specializations = results
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            FileUtil.write(Constant.DB_SPECIALIZATION_NAME, data: results)
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork specialization list error: \(error)")
            self.isLoading = false
        })
    }
}

public class SpecializationDetailViewModel: ObservableObject {
    @Published var specialization = Specialization()
    @Published var isLoading = true
    
    func fetchSpecialization(id: Int) {
        let specializations: [Specialization] = FileUtil.read(Constant.DB_SPECIALIZATION_NAME) ?? []
        specialization = specializations.first(where: { $0.id == id }) ?? Specialization()
        isLoading = false
        
        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }
        
        IoUtil.loadFromNetwork(url, ["id":id], onSuccess: { (result:[Specialization]) in
            guard let firstResult = result.first else { return }
            if (self.specialization != firstResult) {
                FileUtil.update(Constant.DB_SPECIALIZATION_NAME, data: firstResult)
                self.specialization = firstResult
            }
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork specialization detail error: \(error)")
            self.isLoading = false
        })
    }
}
