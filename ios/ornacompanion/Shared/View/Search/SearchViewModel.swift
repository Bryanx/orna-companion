//
//  SearchViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 01/05/2021.
//

import Foundation
import SwiftUI

public class SearchViewModel: ObservableObject {
    @Published var searchResults = [Save]()
    @Published var isLoading = true
    @Published var query = "" {
        didSet {
            if (query.isEmpty || query.count < 2) {
                fetchSearchHistory()
            } else {
                fetchSearchResultsFromDb(query)
            }
        }
    }
    
    func fetchSearchHistory() {
        let result: [Save] = FileUtil.read(Constant.DB_SEARCH_NAME) ?? []
        self.searchResults = result
        isLoading = false
    }
    
    func addToSearchHistory(_ save: Save) {
        let results: [Save] = FileUtil.read(Constant.DB_SEARCH_NAME) ?? []
        FileUtil.write(Constant.DB_SEARCH_NAME, data: [save] + results.filter { $0 != save }.prefix(20))
    }
    
    func fetchSearchResultsFromDb(_ query: String) {
        let skills: [Skill] = FileUtil.read(Constant.DB_SKILL_NAME) ?? []
        let specializations: [Specialization] = FileUtil.read(Constant.DB_SPECIALIZATION_NAME) ?? []
        let npcs: [Npc] = FileUtil.read(Constant.DB_NPC_NAME) ?? []
        let achievements: [Achievement] = FileUtil.read(Constant.DB_ACHIEVEMENT_NAME) ?? []
        self.searchResults = (npcs.map(Save.of) +
                                skills.map(Save.of) +
                                specializations.map(Save.of) +
                                achievements.map(Save.of))
            .filter { $0.name.lowercased().contains(query.lowercased()) }
    }
}

