package nl.bryanderidder.ornaguide.shared.util

import android.content.SharedPreferences
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchResult


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

    fun getDefaultTier(): Int =
        prefs.getString(DEFAULT_TIER, "1")?.toInt() ?: 1

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

        //navigation filters
        const val CHARACTER_CLASS_FILTER_TAB: String = "CHARACTER_CLASS_FILTER_TAB"
        const val SPECIALIZATION_FILTER_TAB: String = "SPECIALIZATION_FILTER_TAB"
        const val SKILL_FILTER_TAB: String = "SKILL_FILTER_TAB"
        const val PET_FILTER_TAB: String = "PET_FILTER_TAB"
        const val MONSTER_FILTER_TAB: String = "MONSTER_FILTER_TAB"
        const val ITEM_FILTER_TAB: String = "ITEM_FILTER_TAB"
        const val NPC_FILTER_TAB: String = "NPC_FILTER_TAB"
        const val ACHIEVEMENT_FILTER_TAB: String = "ACHIEVEMENT_FILTER_TAB"
        const val SAVE_FILTER_TAB: String = "SAVE_FILTER_TAB"

        //settings
        const val DEFAULT_TIER: String = "DEFAULT_TIER"
        const val SAME_SIZE_DISCOVER_ITEMS: String = "SAME_SIZE_DISCOVER_ITEMS"
        const val CRASH_REPORTS: String = "CRASH_REPORTS"
        const val CRASH_REPORTS_REMINDER_SHOWN: String = "CRASH_REPORTS_REMINDER_SHOWN"
    }
}