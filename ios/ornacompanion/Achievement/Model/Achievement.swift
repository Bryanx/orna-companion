//
//  Achievement.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 03/05/2021.
//

import Foundation
import BetterCodable

struct Achievement: Hashable, Codable, Identifiable {
    var id: Int = 0
    var name: String = ""
    var requirement: String = ""
    var tier: Int = 0
    var reward: Int = 0
    
    func getImage() -> String {
        return Constant.ORNA_ICON_IMAGE_PREFIX + "trophy.png"
    }
    
    func formattedReward() -> String {
        return "Reward: \(NumberUtil.formatNumber(reward)) orns"
    }
}
