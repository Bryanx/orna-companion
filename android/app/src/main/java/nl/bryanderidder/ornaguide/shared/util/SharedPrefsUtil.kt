package nl.bryanderidder.ornaguide.shared.util

import android.content.SharedPreferences
import nl.bryanderidder.ornaguide.achievement.ui.list.filter.AchievementFilter
import nl.bryanderidder.ornaguide.characterclass.ui.list.filter.CharacterClassFilter
import nl.bryanderidder.ornaguide.item.ui.list.filter.ItemFilter
import nl.bryanderidder.ornaguide.monster.ui.list.filter.MonsterFilter
import nl.bryanderidder.ornaguide.npc.ui.list.filter.NpcFilter
import nl.bryanderidder.ornaguide.pet.ui.list.filter.PetFilter
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult
import nl.bryanderidder.ornaguide.skill.ui.list.filter.SkillFilter
import nl.bryanderidder.ornaguide.specialization.ui.list.filter.SpecializationFilter


/**
 * Utils for shared prefs. We store session info here.
 * @author Bryan de Ridder
 */
class SharedPrefsUtil(
    private val prefs: SharedPreferences,
    private val converters: OrnaTypeConverters,
) {

    fun setCharacterClassId(value: Int) =
        prefs.edit().putInt(CHARACTER_CLASS_ID, value).apply()

    fun getCharacterClassId(): Int =
        prefs.getInt(CHARACTER_CLASS_ID, 1)

    fun setSpecializationId(value: Int) =
        prefs.edit().putInt(SPECIALIZATION_ID, value).apply()

    fun getSpecializationId(): Int =
        prefs.getInt(SPECIALIZATION_ID, 1)

    fun setSkillId(value: Int) =
        prefs.edit().putInt(SKILL_ID, value).apply()

    fun getSkillId(): Int =
        prefs.getInt(SKILL_ID, 1)

    fun setPetId(value: Int) =
        prefs.edit().putInt(PET_ID, value).apply()

    fun getPetId(): Int =
        prefs.getInt(PET_ID, 1)

    fun setMonsterId(value: Int) =
        prefs.edit().putInt(MONSTER_ID, value).apply()

    fun getMonsterId(): Int =
        prefs.getInt(MONSTER_ID, 1)

    fun setItemId(value: Int) =
        prefs.edit().putInt(ITEM_ID, value).apply()

    fun getItemId(): Int =
        prefs.getInt(ITEM_ID, 1)

    fun setNpcId(value: Int) =
        prefs.edit().putInt(NPC_ID, value).apply()

    fun getNpcId(): Int =
        prefs.getInt(NPC_ID, 1)

    fun setAchievementId(value: Int) =
        prefs.edit().putInt(ACHIEVEMENT_ID, value).apply()

    fun getAchievementId(): Int =
        prefs.getInt(ACHIEVEMENT_ID, 1)

    fun setCharacterClassFilterTab(value: Int) =
        prefs.edit().putInt(CHARACTER_CLASS_FILTER_TAB, value).apply()

    fun getCharacterClassFilterTab(): Int =
        prefs.getInt(CHARACTER_CLASS_FILTER_TAB, 0)

    fun setSpecializationFilterTab(value: Int) =
        prefs.edit().putInt(SPECIALIZATION_FILTER_TAB, value).apply()

    fun getSpecializationFilterTab(): Int =
        prefs.getInt(SPECIALIZATION_FILTER_TAB, 0)

    fun setSkillFilterTab(value: Int) =
        prefs.edit().putInt(SKILL_FILTER_TAB, value).apply()

    fun getSkillFilterTab(): Int =
        prefs.getInt(SKILL_FILTER_TAB, 0)

    fun setPetFilterTab(value: Int) =
        prefs.edit().putInt(PET_FILTER_TAB, value).apply()

    fun getPetFilterTab(): Int =
        prefs.getInt(PET_FILTER_TAB, 0)

    fun setMonsterFilterTab(value: Int) =
        prefs.edit().putInt(MONSTER_FILTER_TAB, value).apply()

    fun getMonsterFilterTab(): Int =
        prefs.getInt(MONSTER_FILTER_TAB, 0)

    fun setItemFilterTab(value: Int) =
        prefs.edit().putInt(ITEM_FILTER_TAB, value).apply()

    fun getItemFilterTab(): Int =
        prefs.getInt(ITEM_FILTER_TAB, 0)

    fun setCharacterClassFilter(value: CharacterClassFilter) =
        prefs.edit().putString(CHARACTER_CLASS_FILTER, converters.toCharacterClassFilter(value)).apply()

    fun getCharacterClassFilter(): CharacterClassFilter =
        converters.fromCharacterClassFilter(prefs.getString(CHARACTER_CLASS_FILTER, "") ?: "")
            ?: CharacterClassFilter(tiers = listOf(DEFAULT_TIER))

    fun setSpecializationFilter(value: SpecializationFilter) =
        prefs.edit().putString(SPECIALIZATION_FILTER, converters.toSpecializationFilter(value)).apply()

    fun getSpecializationFilter(): SpecializationFilter =
        converters.fromSpecializationFilter(prefs.getString(SPECIALIZATION_FILTER, "") ?: "")
            ?: SpecializationFilter()

    fun setSkillFilter(value: SkillFilter) =
        prefs.edit().putString(SKILL_FILTER, converters.toSkillFilter(value)).apply()

    fun getSkillFilter(): SkillFilter =
        converters.fromSkillFilter(prefs.getString(SKILL_FILTER, "") ?: "")
            ?: SkillFilter(tiers = listOf(DEFAULT_TIER))

    fun setPetFilter(value: PetFilter) =
        prefs.edit().putString(PET_FILTER, converters.toPetFilter(value)).apply()

    fun getPetFilter(): PetFilter =
        converters.fromPetFilter(prefs.getString(PET_FILTER, "") ?: "")
            ?: PetFilter(tiers = listOf(DEFAULT_TIER))

    fun setMonsterFilter(value: MonsterFilter) =
        prefs.edit().putString(MONSTER_FILTER, converters.toMonsterFilter(value)).apply()

    fun getMonsterFilter(): MonsterFilter =
        converters.fromMonsterFilter(prefs.getString(MONSTER_FILTER, "") ?: "")
            ?: MonsterFilter(tiers = listOf(DEFAULT_TIER))

    fun setItemFilter(value: ItemFilter) =
        prefs.edit().putString(ITEM_FILTER, converters.toItemFilter(value)).apply()

    fun getItemFilter(): ItemFilter =
        converters.fromItemFilter(prefs.getString(ITEM_FILTER, "") ?: "")
            ?: ItemFilter(tiers = listOf(DEFAULT_TIER))

    fun setNpcFilter(value: NpcFilter) =
        prefs.edit().putString(NPC_FILTER, converters.toNpcFilter(value)).apply()

    fun getNpcFilter(): NpcFilter =
        converters.fromNpcFilter(prefs.getString(NPC_FILTER, "") ?: "")
            ?: NpcFilter()

    fun setAchievementFilter(value: AchievementFilter) =
        prefs.edit().putString(ACHIEVEMENT_FILTER, converters.toAchievementFilter(value)).apply()

    fun getAchievementFilter(): AchievementFilter =
        converters.fromAchievementFilter(prefs.getString(ACHIEVEMENT_FILTER, "") ?: "")
            ?: AchievementFilter(tiers = listOf(DEFAULT_TIER))

    fun setNpcFilterTab(value: Int) =
        prefs.edit().putInt(NPC_FILTER_TAB, value).apply()

    fun getNpcFilterTab(): Int =
        prefs.getInt(NPC_FILTER_TAB, 0)

    fun setAchievementFilterTab(value: Int) =
        prefs.edit().putInt(ACHIEVEMENT_FILTER_TAB, value).apply()

    fun getAchievementFilterTab(): Int =
        prefs.getInt(ACHIEVEMENT_FILTER_TAB, 0)

    fun setSaveFilterTab(value: Int) =
        prefs.edit().putInt(SAVE_FILTER_TAB, value).apply()

    fun getSaveFilterTab(): Int =
        prefs.getInt(SAVE_FILTER_TAB, 0)

    fun isSameSizeDiscoverItems(): Boolean =
        prefs.getBoolean(SAME_SIZE_DISCOVER_ITEMS, false)

    fun isCrashReportsEnabled(): Boolean =
        prefs.getBoolean(CRASH_REPORTS, false)

    fun setCrashReportsEnabled(value: Boolean) =
        prefs.edit().putBoolean(CRASH_REPORTS, value).apply()

    fun isCrashReportsReminderShown(): Boolean =
        prefs.getBoolean(CRASH_REPORTS_REMINDER_SHOWN, false)

    fun setCrashReportsReminderShown() =
        prefs.edit().putBoolean(CRASH_REPORTS_REMINDER_SHOWN, true).apply()

    fun writeLong(key: String, value: Long) =
        prefs.edit().putLong(key, value).apply()

    fun readLong(key: String): Long =
        prefs.getLong(key, 0L)

    fun isFirstStart(): Boolean = prefs.getBoolean(IS_FIRST_START, true).also { isTrue ->
        if (isTrue) prefs.edit().putBoolean(IS_FIRST_START, false).apply()
    }

    fun getSearchHistory(): List<SearchResult> =
        converters.fromSearchResult(prefs.getString(SEARCH_HISTORY, "[]") ?: "[]")

    fun addToSearchHistory(result: SearchResult) {
        val newSearchHistory = listOf(result) + getSearchHistory().filter { it != result }.take(19)
        val json = converters.toSearchResult(newSearchHistory)
        prefs.edit().putString(SEARCH_HISTORY, json).apply()
    }

    fun clearAllFilters() {
        val editor = prefs.edit()
        listOf(CHARACTER_CLASS_FILTER, CHARACTER_CLASS_FILTER_TAB, SPECIALIZATION_FILTER,
            SPECIALIZATION_FILTER_TAB, SKILL_FILTER, SKILL_FILTER_TAB, PET_FILTER, PET_FILTER_TAB,
            MONSTER_FILTER, MONSTER_FILTER_TAB, ITEM_FILTER, ITEM_FILTER_TAB, NPC_FILTER,
            NPC_FILTER_TAB, ACHIEVEMENT_FILTER, ACHIEVEMENT_FILTER_TAB, SAVE_FILTER_TAB)
            .forEach(editor::remove)
        editor.apply()
    }

    companion object {
        const val IS_FIRST_START: String = "IS_FIRST_START"
        const val SEARCH_HISTORY: String = "SEARCH_HISTORY"

        //navigation
        const val CHARACTER_CLASS_ID: String = "CHARACTER_CLASS_ID"
        const val SPECIALIZATION_ID: String = "SPECIALIZATION_ID"
        const val SKILL_ID: String = "SKILL_ID"
        const val PET_ID: String = "PET_ID"
        const val MONSTER_ID: String = "MONSTER_ID"
        const val ITEM_ID: String = "ITEM_ID"
        const val NPC_ID: String = "NPC_ID"
        const val ACHIEVEMENT_ID: String = "ACHIEVEMENT_ID"

        //filters
        const val CHARACTER_CLASS_FILTER: String = "CHARACTER_CLASS_FILTER"
        const val CHARACTER_CLASS_FILTER_TAB: String = "CHARACTER_CLASS_FILTER_TAB"
        const val SPECIALIZATION_FILTER: String = "SPECIALIZATION_FILTER"
        const val SPECIALIZATION_FILTER_TAB: String = "SPECIALIZATION_FILTER_TAB"
        const val SKILL_FILTER: String = "SKILL_FILTER"
        const val SKILL_FILTER_TAB: String = "SKILL_FILTER_TAB"
        const val PET_FILTER: String = "PET_FILTER"
        const val PET_FILTER_TAB: String = "PET_FILTER_TAB"
        const val MONSTER_FILTER: String = "MONSTER_FILTER"
        const val MONSTER_FILTER_TAB: String = "MONSTER_FILTER_TAB"
        const val ITEM_FILTER: String = "ITEM_FILTER"
        const val ITEM_FILTER_TAB: String = "ITEM_FILTER_TAB"
        const val NPC_FILTER: String = "NPC_FILTER"
        const val NPC_FILTER_TAB: String = "NPC_FILTER_TAB"
        const val ACHIEVEMENT_FILTER: String = "ACHIEVEMENT_FILTER"
        const val ACHIEVEMENT_FILTER_TAB: String = "ACHIEVEMENT_FILTER_TAB"
        const val SAVE_FILTER_TAB: String = "SAVE_FILTER_TAB"

        //settings
        const val DEFAULT_TIER: Int = 1
        const val SAME_SIZE_DISCOVER_ITEMS: String = "SAME_SIZE_DISCOVER_ITEMS"
        const val CRASH_REPORTS: String = "CRASH_REPORTS"
        const val CRASH_REPORTS_REMINDER_SHOWN: String = "CRASH_REPORTS_REMINDER_SHOWN"
    }
}