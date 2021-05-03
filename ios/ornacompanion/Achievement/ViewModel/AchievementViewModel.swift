//
//  AchievementViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 03/05/2021.
//

import Foundation
import SwiftUI

public class AchievementViewModel: ObservableObject {
    @Published var achievements = [Achievement]()
    @Published var allPossibleTiers = [Int]()
    @Published var selectedTiers = [1]
    @Published var isLoading = true
    
    func fetchAllPossibleTiers() {
        guard self.allPossibleTiers.isEmpty else { return }
        let result: [Achievement] = FileUtil.read(Constant.DB_ACHIEVEMENT_NAME) ?? []
        self.allPossibleTiers = result.map { $0.tier }.distinct().sorted()
    }
    
    func applyFilter(_ selectedTiers: [Int]) {
        self.selectedTiers = selectedTiers
        fetchAchievements()
    }
    
    func fetchAchievements() {
        let result: [Achievement] = FileUtil.read(Constant.DB_ACHIEVEMENT_NAME) ?? []
        if !result.isEmpty {
            self.achievements = result
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            isLoading = false
            return
        }
        
        guard let url = URL(string: Constant.ACHIEVEMENT_URL) else { return }
        
        IoUtil.loadFromNetwork(url, onSuccess: { (results:[Achievement]) in
            print("loadFromNetwork achievements success")
            self.achievements = results
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            FileUtil.write(Constant.DB_ACHIEVEMENT_NAME, data: results)
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork achievements error: \(error)")
            self.isLoading = false
        })
    }
}

public class AchievementDetailViewModel: ObservableObject {
    @Published var achievement = Achievement()
    @Published var isLoading = true
    
    func fetchAchievement(id: Int) {
        let achievements: [Achievement] = FileUtil.read(Constant.DB_ACHIEVEMENT_NAME) ?? []
        achievement = achievements.first(where: { $0.id == id }) ?? Achievement()
        isLoading = false
        
        guard let url = URL(string: Constant.ACHIEVEMENT_URL) else { return }
        
        IoUtil.loadFromNetwork(url, ["id":id], onSuccess: { (result:[Achievement]) in
            guard let firstResult = result.first else { return }
            if (self.achievement != firstResult) {
                FileUtil.update(Constant.DB_ACHIEVEMENT_NAME, data: firstResult)
                self.achievement = firstResult
            }
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork achievement detail error: \(error)")
            self.isLoading = false
        })
    }
}
