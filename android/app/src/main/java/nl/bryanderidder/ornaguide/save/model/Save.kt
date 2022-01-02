package nl.bryanderidder.ornaguide.save.model

import androidx.room.Entity
import androidx.room.Ignore
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.specialization.model.Specialization

@Entity(primaryKeys = ["id", "type"])
data class Save(
    val id: Int,
    val name: String,
    val subText: String,
    val type: String,
    val image: String,
    val tier: Int
) {
    @Ignore var isFiltered: Boolean = true

    companion object {
        fun ofCharacterClass(it: CharacterClass): Save =
            Save(it.id, it.name, it.formattedLearns(), CharacterClass.NAME, it.previewImageUrl, it.tier)

        fun ofSkill(it: Skill): Save =
            Save(it.id, it.name, it.description, Skill.NAME, it.previewImageUrl, it.tier)

        fun ofSpecialization(it: Specialization): Save =
            Save(it.id, it.name, it.description, Specialization.NAME, it.previewImageUrl, it.tier)

        fun ofPet(it: Pet): Save =
            Save(it.id, it.name, it.description, Pet.NAME, it.previewImageUrl, it.tier)

        fun ofItem(it: Item): Save =
            Save(it.id, it.name, it.type, Item.NAME, it.previewImageUrl, it.tier)

        fun ofMonster(it: Monster): Save =
            Save(it.id, it.name, it.searchFormat(), Monster.NAME, it.previewImageUrl, it.tier)

        fun ofNpc(it: Npc): Save =
            Save(it.id, it.name, it.description, Npc.NAME, it.previewImageUrl, it.tier)

        fun ofAchievement(it: Achievement): Save =
            Save(it.id, it.name, it.requirement, Achievement.NAME, it.previewImageUrl, it.tier)
    }
}