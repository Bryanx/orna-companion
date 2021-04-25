//
//  Skill.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 22/04/2021.
//

import Foundation
import BetterCodable

struct Skill: Hashable, Codable, Identifiable {
    var id: Int = 0
    @DefaultFalse var bought: Bool = false
    @DefaultToZero var cost: Int = 0
    @DefaultEmptyArray var gives: [String] = []
    @DefaultEmptyArray var causes: [String] = []
    var description: String = ""
    @DefaultToBlank var element: String = ""
    @DefaultFalse var isMagic: Bool = false
    @DefaultEmptyArray var learnedBy: [LearnedBy] = []
    @DefaultToZero var manaCost: Int = 0
    @DefaultEmptyArray var monstersUse: [IdNamePair] = []
    @DefaultEmptyArray var petsUse: [IdNamePair] = []
    var name: String = ""
    var tier: Int = 0
    var type: String = ""
    
    func getImage() -> String {
        if type.contains("Attack") {
            return "\(Constant.ORNA_ICON_IMAGE_PREFIX)weapon.png"
        } else if type.contains("Passive") {
            return "\(Constant.ORNA_ICON_IMAGE_PREFIX)time.png"
        } else {
            return "\(Constant.ORNA_ICON_IMAGE_PREFIX)staff.png"
        }
    }
    
    func formattedTypeAndMana() -> String {
        return manaCost == 0 ? type : "\(type) - \(manaCost) mana"
    }
    
    func formattedCost() -> String {
        if cost == 0 {
            return ""
        } else {
            return "Bought from Arcanist\n\(NumberUtil.formatNumber(cost)) gold"
        }
    }
    
    func formattedGives() -> String {
        return "Gives:\n\(gives.joined(separator: "\n"))"
    }
    
    func formattedCauses() -> String {
        return "Causes:\n\(causes.joined(separator: "\n"))"
    }
    
    struct IdNamePair: Hashable, Codable, Identifiable {
        var id: Int = 0
        var name: String = ""
    }
    
    struct LearnedBy: Hashable, Codable, Identifiable {
        var id: Int = 0
        var level: Int = 0
        var name: String = ""
        @DefaultFalse var specialization: Bool = false
    }
}
