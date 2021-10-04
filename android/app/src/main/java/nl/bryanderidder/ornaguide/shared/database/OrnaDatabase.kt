package nl.bryanderidder.ornaguide.shared.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.model.AchievementFTS
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementDao
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClassFTS
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassDao
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.model.ItemFTS
import nl.bryanderidder.ornaguide.item.persistence.ItemDao
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.model.MonsterFTS
import nl.bryanderidder.ornaguide.monster.persistence.MonsterDao
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.model.NpcFTS
import nl.bryanderidder.ornaguide.npc.persistence.NpcDao
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.model.PetFTS
import nl.bryanderidder.ornaguide.pet.persistence.PetDao
import nl.bryanderidder.ornaguide.save.model.Save
import nl.bryanderidder.ornaguide.save.persistence.SaveDao
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.model.SkillFTS
import nl.bryanderidder.ornaguide.skill.persistence.SkillDao
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.model.SpecializationFTS
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationDao


/**
 * Main database
 * @author Bryan de Ridder
 */
@Database(
    entities = [
        CharacterClass::class,
        CharacterClassFTS::class,
        Skill::class,
        SkillFTS::class,
        Specialization::class,
        SpecializationFTS::class,
        Pet::class,
        PetFTS::class,
        Item::class,
        ItemFTS::class,
        Monster::class,
        MonsterFTS::class,
        Npc::class,
        NpcFTS::class,
        Achievement::class,
        AchievementFTS::class,
        Save::class,
    ],
    version = 4
)
@TypeConverters(OrnaTypeConverters::class)
abstract class OrnaDatabase : RoomDatabase() {
    abstract fun characterClassDao(): CharacterClassDao
    abstract fun skillDao(): SkillDao
    abstract fun specializationDao(): SpecializationDao
    abstract fun petDao(): PetDao
    abstract fun itemDao(): ItemDao
    abstract fun monsterDao(): MonsterDao
    abstract fun npcDao(): NpcDao
    abstract fun achievementDao() : AchievementDao
    abstract fun saveDao() : SaveDao
}