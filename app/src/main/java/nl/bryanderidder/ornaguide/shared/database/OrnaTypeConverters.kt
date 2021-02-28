package nl.bryanderidder.ornaguide.shared.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.skill.model.Skill


/**
 * Type converters
 * @author Bryan de Ridder
 */
@ProvidedTypeConverter
class OrnaTypeConverters(private val moshi: Moshi, ) {

    private val jsonAdapter: JsonAdapter<List<String>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, String::class.java))

    @TypeConverter
    fun fromStringList(value: List<String>): String = jsonAdapter.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> = jsonAdapter.fromJson(value) ?: listOf()

    @TypeConverter
    fun fromCharacterClassLearn(type: List<CharacterClass.Learn>): String {
        val listType = Types.newParameterizedType(List::class.java, CharacterClass.Learn::class.java)
        val adapter: JsonAdapter<List<CharacterClass.Learn>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toCharacterClassLearn(value: String): List<CharacterClass.Learn> {
        val listType = Types.newParameterizedType(List::class.java, CharacterClass.Learn::class.java)
        val adapter: JsonAdapter<List<CharacterClass.Learn>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromCharacterClassPassive(type: List<CharacterClass.Passive>): String {
        val listType = Types.newParameterizedType(List::class.java, CharacterClass.Passive::class.java)
        val adapter: JsonAdapter<List<CharacterClass.Passive>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toCharacterClassPassive(value: String): List<CharacterClass.Passive> {
        val listType = Types.newParameterizedType(List::class.java, CharacterClass.Passive::class.java)
        val adapter: JsonAdapter<List<CharacterClass.Passive>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromSkillLearnedBy(type: List<Skill.LearnedBy>): String {
        val listType = Types.newParameterizedType(List::class.java, Skill.LearnedBy::class.java)
        val adapter: JsonAdapter<List<Skill.LearnedBy>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toSkillLearnedBy(value: String): List<Skill.LearnedBy> {
        val listType = Types.newParameterizedType(List::class.java, Skill.LearnedBy::class.java)
        val adapter: JsonAdapter<List<Skill.LearnedBy>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromSkillMonstersUse(type: List<Skill.MonstersUse>): String {
        val listType = Types.newParameterizedType(List::class.java, Skill.MonstersUse::class.java)
        val adapter: JsonAdapter<List<Skill.MonstersUse>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toSkillMonstersUse(value: String): List<Skill.MonstersUse> {
        val listType = Types.newParameterizedType(List::class.java, Skill.MonstersUse::class.java)
        val adapter: JsonAdapter<List<Skill.MonstersUse>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }
}