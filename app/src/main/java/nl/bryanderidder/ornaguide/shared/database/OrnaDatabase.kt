package nl.bryanderidder.ornaguide.shared.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassDao
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.persistence.SkillDao


/**
 * Main database
 * @author Bryan de Ridder
 */
@Database(entities = [CharacterClass::class, Skill::class], version = 1, exportSchema = true)
@TypeConverters(OrnaTypeConverters::class)
abstract class OrnaDatabase : RoomDatabase() {
    abstract fun characterClassDao(): CharacterClassDao
    abstract fun skillDao(): SkillDao
}