package nl.bryanderidder.ornaguide.shared.ui.menu.search

import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.specialization.model.Specialization

data class SearchResult(
    val id: Int,
    val name: String,
    val subText: String,
    val type: String,
    val image: String,
    val tier: Int
) {
    companion object {
        fun ofCharacterClass(it: CharacterClass): SearchResult =
            SearchResult(it.id, it.name, it.formattedLearns(), CharacterClass.NAME, it.previewImageUrl, it.tier)

        fun ofSkill(it: Skill): SearchResult =
            SearchResult(it.id, it.name, it.description, Skill.NAME, it.previewImageUrl, it.tier)

        fun ofSpecialization(it: Specialization): SearchResult =
            SearchResult(it.id, it.name, it.description, Specialization.NAME, it.previewImageUrl, it.tier)

        fun ofPet(it: Pet): SearchResult =
            SearchResult(it.id, it.name, it.description, Pet.NAME, it.previewImageUrl, it.tier)

        fun ofItem(it: Item): SearchResult =
            SearchResult(it.id, it.name, it.type, Item.NAME, it.previewImageUrl, it.tier)

        fun ofMonster(it: Monster): SearchResult =
            SearchResult(it.id, it.name, it.searchFormat(), Monster.NAME, it.previewImageUrl, it.tier)

        fun ofNpc(it: Npc): SearchResult =
            SearchResult(it.id, it.name, it.description, Npc.NAME, it.previewImageUrl, it.tier)

        fun ofAchievement(it: Achievement): SearchResult =
            SearchResult(it.id, it.name, it.requirement, Achievement.NAME, it.previewImageUrl, it.tier)
    }
}