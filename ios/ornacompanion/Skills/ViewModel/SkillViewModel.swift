//
//  SkillViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import Foundation
import SwiftUI

public class SkillViewModel: ObservableObject {
    @Published var skills = [Skill]()
    @Published var allPossibleTiers = [Int]()
    @Published var selectedTiers = [1]
    @Published var isLoading = true
    
    func fetchAllPossibleTiers() {
        guard self.allPossibleTiers.isEmpty else { return }
        let result: [Skill] = FileUtil.read(Constant.DB_SKILL_NAME) ?? []
        self.allPossibleTiers = result.map { $0.tier }.distinct().sorted()
    }
    
    func applyFilter(_ selectedTiers: [Int]) {
        self.selectedTiers = selectedTiers
        fetchSkills()
    }
    
    func fetchSkills() {
        let result: [Skill] = FileUtil.read(Constant.DB_SKILL_NAME) ?? []
        if !result.isEmpty {
            self.skills = result
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            isLoading = false
            return
        }
        
        guard let url = URL(string: Constant.SKILL_URL) else { return }
        
        IoUtil.loadFromNetwork(url, onSuccess: { (results:[Skill]) in
            print("loadFromNetwork skills success")
            self.skills = results
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            FileUtil.write(Constant.DB_SKILL_NAME, data: results)
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork skills error: \(error)")
            self.isLoading = false
        })
    }
}

public class SkillDetailViewModel: ObservableObject {
    @Published var skill = Skill()
    @Published var isLoading = true
    
    func fetchSkill(id: Int) {
        let skills: [Skill] = FileUtil.read(Constant.DB_SKILL_NAME) ?? []
        skill = skills.first(where: { $0.id == id }) ?? Skill()
        isLoading = false
        
        guard let url = URL(string: Constant.SKILL_URL) else { return }
        
        IoUtil.loadFromNetwork(url, ["id":id], onSuccess: { (result:[Skill]) in
            guard let firstResult = result.first else { return }
            if (self.skill != firstResult) {
                FileUtil.update(Constant.DB_SKILL_NAME, data: firstResult)
                self.skill = firstResult
            }
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork skill detail error: \(error)")
            self.isLoading = false
        })
    }
}
