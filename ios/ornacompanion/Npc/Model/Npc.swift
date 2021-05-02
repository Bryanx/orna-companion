//
//  Npc.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 02/05/2021.
//

import Foundation
import BetterCodable

struct Npc: Hashable, Codable, Identifiable {
    var id: Int = 0
    var description: String = ""
    var name: String = ""
    var image: String = ""
    var tier: Int = 0
    @DefaultEmptyArray var quests: [Quest] = []
    
    func getImage() -> String {
        return Constant.ORNA_IMAGE_PREFIX + image
    }
    
    struct Quest: Hashable, Codable, Identifiable {
        var id: Int = 0
        var name: String = ""
        var tier: Int = 0
    }
}
