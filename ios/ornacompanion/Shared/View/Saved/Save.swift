//
//  Save.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 25/04/2021.
//

import Foundation

struct Save: Hashable, Codable, Identifiable {
    var id: Int = 0
    var name: String = ""
    var subText: String = ""
    var type: String = ""
    var image: String = ""
    var tier: Int = 1
    var uid: String {
        return "\(type)\(id)"
    }
    
//    func ofCharacterClass(it: CharacterClass) -> Save {
//        return Save(it.id, it.name, it.formattedLearns(), CharacterClass.NAME, it.previewImageUrl, it.tier)
//    }
//
//    func ofPet(it: Pet): Save =
//        Save(it.id, it.name, it.description, Pet.NAME, it.previewImageUrl, it.tier)
//
//    func ofItem(it: Item): Save =
//        Save(it.id, it.name, it.type, Item.NAME, it.previewImageUrl, it.tier)
//
//    func ofMonster(it: Monster): Save =
//        Save(it.id, it.name, it.searchFormat(), Monster.NAME, it.previewImageUrl, it.tier)
//
//    func ofNpc(it: Npc): Save =
//        Save(it.id, it.name, it.description, Npc.NAME, it.previewImageUrl, it.tier)
//
//    func ofAchievement(it: Achievement): Save =
//        Save(it.id, it.name, it.requirement, Achievement.NAME, it.previewImageUrl, it.tier)
}

extension Save {
    static func of(_ it: Skill) -> Save {
        return Save(id: it.id,
                    name: it.name,
                    subText: it.description,
                    type: Constant.SKILL,
                    image: it.getImage(),
                    tier: it.tier)
    }
    
    static func of(_ it: Specialization) -> Save {
        return Save(id: it.id,
                    name: it.name,
                    subText: it.description,
                    type: Constant.SPECIALIZATION,
                    image: it.getImage(),
                    tier: it.tier)
    }
    
    static func of(_ it: Npc) -> Save {
        return Save(id: it.id,
                    name: it.name,
                    subText: it.description,
                    type: Constant.NPC,
                    image: it.getImage(),
                    tier: it.tier)
    }
    
    static func of(_ it: Achievement) -> Save {
        return Save(id: it.id,
                    name: it.name,
                    subText: it.requirement,
                    type: Constant.ACHIEVEMENT,
                    image: it.getImage(),
                    tier: it.tier)
    }
}
