package nl.bryanderidder.ornaguide.shared.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.specialization.model.Specialization


/**
 * Type converters
 * @author Bryan de Ridder
 */
@ProvidedTypeConverter
class OrnaTypeConverters(private val moshi: Moshi) {

    private val jsonAdapter: JsonAdapter<List<String>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, String::class.java))

    @TypeConverter
    fun fromStringList(value: List<String>): String = jsonAdapter.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> = when {
        value.isEmpty() -> listOf()
        else -> jsonAdapter.fromJson(value) ?: listOf()
    }

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
    fun fromSkillIdNamePair(type: List<Skill.IdNamePair>): String {
        val listType = Types.newParameterizedType(List::class.java, Skill.IdNamePair::class.java)
        val adapter: JsonAdapter<List<Skill.IdNamePair>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toSkillIdNamePair(value: String): List<Skill.IdNamePair> {
        val listType = Types.newParameterizedType(List::class.java, Skill.IdNamePair::class.java)
        val adapter: JsonAdapter<List<Skill.IdNamePair>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromBoost(type: List<Specialization.Boost>): String {
        val listType = Types.newParameterizedType(List::class.java, Specialization.Boost::class.java)
        val adapter: JsonAdapter<List<Specialization.Boost>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toBoost(value: String): List<Specialization.Boost> {
        val listType = Types.newParameterizedType(List::class.java, Specialization.Boost::class.java)
        val adapter: JsonAdapter<List<Specialization.Boost>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromSkill(type: List<Pet.Skill>): String {
        val listType = Types.newParameterizedType(List::class.java, Pet.Skill::class.java)
        val adapter: JsonAdapter<List<Pet.Skill>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toSkill(value: String): List<Pet.Skill> {
        val listType = Types.newParameterizedType(List::class.java, Pet.Skill::class.java)
        val adapter: JsonAdapter<List<Pet.Skill>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromIdNamePair(type: List<Item.IdNamePair>): String {
        val listType = Types.newParameterizedType(List::class.java, Item.IdNamePair::class.java)
        val adapter: JsonAdapter<List<Item.IdNamePair>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toIdNamePair(value: String): List<Item.IdNamePair> {
        val listType = Types.newParameterizedType(List::class.java, Item.IdNamePair::class.java)
        val adapter: JsonAdapter<List<Item.IdNamePair>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromMonsterIdNamePair(type: List<Monster.IdNamePair>): String {
        val listType = Types.newParameterizedType(List::class.java, Monster.IdNamePair::class.java)
        val adapter: JsonAdapter<List<Monster.IdNamePair>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun toMonsterIdNamePair(value: String): List<Monster.IdNamePair> {
        val listType = Types.newParameterizedType(List::class.java, Monster.IdNamePair::class.java)
        val adapter: JsonAdapter<List<Monster.IdNamePair>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    @TypeConverter
    fun fromNpcQuest(type: List<Npc.Quest>): String {
        val listType = Types.newParameterizedType(List::class.java, Npc.Quest::class.java)
        val adapter: JsonAdapter<List<Npc.Quest>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }

    @TypeConverter
    fun fromNpcQuest(value: String): List<Npc.Quest> {
        val listType = Types.newParameterizedType(List::class.java, Npc.Quest::class.java)
        val adapter: JsonAdapter<List<Npc.Quest>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    fun fromSearchResult(value: String): List<SearchResult> {
        val listType = Types.newParameterizedType(List::class.java, SearchResult::class.java)
        val adapter: JsonAdapter<List<SearchResult>> = moshi.adapter(listType)
        return adapter.fromJson(value) ?: listOf()
    }

    fun toSearchResult(type: List<SearchResult>): String {
        val listType = Types.newParameterizedType(List::class.java, SearchResult::class.java)
        val adapter: JsonAdapter<List<SearchResult>> = moshi.adapter(listType)
        return adapter.toJson(type)
    }
}