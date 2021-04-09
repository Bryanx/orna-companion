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
    var images: [String] = []
    var tier: Int = 1
    var cost: String = ""
    var boosts: [Boost] = []
    
    func getImage() -> String {
        if images.count == 0 {
          return ""
        } else {
          return Constant.ORNA_IMAGE_PREFIX + images[images.count - 1]
        }
    }
    
    static let SAMPLE = Specialization(
        id: 56,
        name: "Inquisitor",
        images: ["subclasses/apprentice/default_m.png"],
        tier: 7,
        cost: "50,000 orns",
        boosts: [Boost(name: "Attack", value: 5), Boost(name: "Hit points", value: -5)]
    )
    
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
}
