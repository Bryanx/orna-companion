//
//  SaveViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 25/04/2021.
//

import Foundation
import SwiftUI

public class SaveViewModel: ObservableObject {
    @Published var saves = [Save]()
    @Published var isSaved = false
    @Published var isLoading = true
    
    func fetchSaves() {
        let result: [Save] = FileUtil.read(Constant.DB_SAVE_NAME) ?? []
        self.saves = result
        isLoading = false
    }
    
    func fetchIsSaved(_ save: Save) {
        let results: [Save] = FileUtil.read(Constant.DB_SAVE_NAME) ?? []
        self.isSaved = results.contains(save)
    }
    
    func toggleSave(_ save: Save) {
        let results: [Save] = FileUtil.read(Constant.DB_SAVE_NAME) ?? []
        if (self.isSaved) {
            FileUtil.write(Constant.DB_SAVE_NAME, data: results.filter { $0 != save })
            self.isSaved = false
        } else {
            FileUtil.write(Constant.DB_SAVE_NAME, data: results + [save])
            self.isSaved = true
        }
    }
}
