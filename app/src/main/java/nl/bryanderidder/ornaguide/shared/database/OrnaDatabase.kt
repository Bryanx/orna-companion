package nl.bryanderidder.ornaguide.shared.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementDao
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassDao
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.persistence.ItemDao
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.persistence.MonsterDao
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.persistence.NpcDao
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.persistence.PetDao
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillDao
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationDao


/**
 * Main database
 * @author Bryan de Ridder
 */
@Database(
    entities = [
        CharacterClass::class,
        Skill::class,
        Specialization::class,
        Pet::class,
        Item::class,
        Monster::class,
        Npc::class,
        Achievement::class,
    ],
    version = 2
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
}