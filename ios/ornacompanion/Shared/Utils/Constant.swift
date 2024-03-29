//
//  Constants.swift
//  ornacompanion
//
//  Created by Bryan de Ridder on 07/04/2021.
//

struct Constant {
    static let BASE_URL = "https://orna.guide/api/v1/"
    static let SPECIALIZATION_URL = BASE_URL + "specialization"
    static let SKILL_URL = BASE_URL + "skill"
    static let NPC_URL = BASE_URL + "npc"
    static let ACHIEVEMENT_URL = BASE_URL + "achievement"
    static let ORNA_IMAGE_PREFIX = "https://orna.guide/static/orna/img/"
    static let ORNA_ICON_IMAGE_PREFIX = "https://orna.guide/static/orna/img/icons/"
    
    //Entities
    static let SKILL = "Skill"
    static let SPECIALIZATION = "Specialization"
    static let NPC = "Npc"
    static let ACHIEVEMENT = "Achievement"
    
    // cache files
    static let DB_SPECIALIZATION_NAME = "SpecializationResponse.json"
    static let DB_SKILL_NAME = "SkillResponse.json"
    static let DB_NPC_NAME = "NpcResponse.json"
    static let DB_ACHIEVEMENT_NAME = "AchievementResponse.json"
    static let DB_SAVE_NAME = "Saves.json"
    static let DB_SEARCH_NAME = "Searches.json"
}
