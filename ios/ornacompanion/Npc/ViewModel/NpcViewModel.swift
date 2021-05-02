//
//  NpcViewModel.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 02/05/2021.
//

import Foundation
import SwiftUI

public class NpcViewModel: ObservableObject {
    @Published var npcs = [Npc]()
    @Published var allPossibleTiers = [Int]()
    @Published var selectedTiers = [1]
    @Published var isLoading = true
    
    func fetchAllPossibleTiers() {
        guard self.allPossibleTiers.isEmpty else { return }
        let result: [Npc] = FileUtil.read(Constant.DB_NPC_NAME) ?? []
        self.allPossibleTiers = result.map { $0.tier }.distinct().sorted()
    }
    
    func applyFilter(_ selectedTiers: [Int]) {
        self.selectedTiers = selectedTiers
        fetchNpcs()
    }
    
    func fetchNpcs() {
        let result: [Npc] = FileUtil.read(Constant.DB_NPC_NAME) ?? []
        if !result.isEmpty {
            self.npcs = result
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            isLoading = false
            return
        }
        
        guard let url = URL(string: Constant.NPC_URL) else { return }
        
        IoUtil.loadFromNetwork(url, onSuccess: { (results:[Npc]) in
            print("loadFromNetwork npcs success")
            self.npcs = results
                .sorted { $0.tier < $1.tier}
                .filter { self.selectedTiers.contains($0.tier) }
            FileUtil.write(Constant.DB_NPC_NAME, data: results)
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork npcs error: \(error)")
            self.isLoading = false
        })
    }
}

public class NpcDetailViewModel: ObservableObject {
    @Published var npc = Npc()
    @Published var isLoading = true
    
    func fetchNpc(id: Int) {
        let npcs: [Npc] = FileUtil.read(Constant.DB_NPC_NAME) ?? []
        npc = npcs.first(where: { $0.id == id }) ?? Npc()
        isLoading = false
        
        guard let url = URL(string: Constant.NPC_URL) else { return }
        
        IoUtil.loadFromNetwork(url, ["id":id], onSuccess: { (result:[Npc]) in
            guard let firstResult = result.first else { return }
            if (self.npc != firstResult) {
                FileUtil.update(Constant.DB_NPC_NAME, data: firstResult)
                self.npc = firstResult
            }
            self.isLoading = false
        }, onError: { error in
            print("loadFromNetwork npc detail error: \(error)")
            self.isLoading = false
        })
    }
}
