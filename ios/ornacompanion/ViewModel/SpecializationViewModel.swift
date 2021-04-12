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
    @Published var isLoading = true
    
    func fetchSpecializations() {
        let result: [Specialization] = FileUtil.read("SpecializationResponse.json") ?? []
        if !result.isEmpty {
            self.specializations = result
            isLoading = false
            return
        }
        
        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }
        
        IoUtil.loadFromNetwork(url, onSuccess: { (results:[Specialization]) in
            self.specializations = results
            FileUtil.write("SpecializationResponse.json", data: self.specializations)
            self.isLoading = false
        }, onError: {
            self.isLoading = false
        })
    }
}

public class SpecializationDetailViewModel: ObservableObject {
    @Published var specialization = Specialization()
    @Published var isLoading = true
    
    func fetchSpecialization(id: Int) {
        let specializations: [Specialization] = FileUtil.read("SpecializationResponse.json") ?? []
        specialization = specializations.first(where: { $0.id == id }) ?? Specialization()
        isLoading = false
        
        guard let url = URL(string: Constant.SPECIALIZATION_URL) else { return }
        
        IoUtil.loadFromNetwork(url, ["id":id], onSuccess: { (result:[Specialization]) in
            guard let firstResult = result.first else { return }
            if (self.specialization != firstResult) {
                FileUtil.update("SpecializationResponse.json", data: firstResult)
                self.specialization = firstResult
            }
            self.isLoading = false
        }, onError: {
            self.isLoading = false
        })
    }
}
