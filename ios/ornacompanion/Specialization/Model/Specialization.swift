//
//  Item.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

import Foundation

struct Specialization: Hashable, Codable, Identifiable {
    var id: Int = 0
    var name: String = ""
    var femaleName: String = ""
    var cost: String = ""
    var price: String = ""
    var description: String = ""
    var tier: Int = 1
    var images: [String] = []
    var skills: Skills = Skills()
    var boosts: [Boost] = []
    
    func getImage() -> String {
        if images.count == 0 {
          return ""
        } else {
          return Constant.ORNA_IMAGE_PREFIX + images[images.count - 1]
        }
    }
    
    func formattedCost() -> String {
        if cost.isEmpty {
            return "\(NumberUtil.formatNumber(price)) orns"
        } else {
            return cost
        }
    }
    
    struct Skills: Hashable, Codable {
        var passives: [Passive] = []
        var learns: [Learn] = []
        var slots: Int = 0
    }
    
    struct Learn: Hashable, Codable, Identifiable {
        var id: Int = 0
        var level: Int = 0
        var name: String = ""
        var type: String = ""
    }
    
    struct Passive: Hashable, Codable, Identifiable {
        var id: Int = 0
        var name: String = ""
    }
    
    struct Boost: Hashable, Codable {
        var name: String
        var value: Int
        
        func formattedName() -> String {
            switch name.lowercased() {
            case "hit points" : return "HP"
            case "attack" : return "Att"
            case "defense" : return "Def"
            case "resistance" : return "Res"
            case "magic" : return "Mag"
            case "dexterity" : return "Dex"
            case "mana" : return "Mana"
            default: return "?"
            }
        }
        
        func formattedValue() -> String {
            if value >= 0 {
                return "+\(value)%"
            } else {
                return "\(value)%"
            }
        }
    }
    
    static let SAMPLE = Specialization(
        id: 56,
        name: "Inquisitor",
        cost: "50,000 orns",
        description: "A long forgotten Inquisitor of Avalon. Learns Holy and defensive skills.",
        tier: 7,
        images: ["subclasses/apprentice/default_m.png"],
        boosts: [Boost(name: "Attack", value: 5), Boost(name: "Hit points", value: -5)]
    )
}
