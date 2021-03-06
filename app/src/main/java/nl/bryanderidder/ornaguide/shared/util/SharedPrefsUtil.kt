package nl.bryanderidder.ornaguide.shared.util

import android.content.SharedPreferences


/**
 * Utils for shared prefs. We store session info here.
 * @author Bryan de Ridder
 */
class SharedPrefsUtil(private val prefs: SharedPreferences) {

    fun setCharacterClassId(value: Int) =
        prefs.edit().putInt(CHARACTER_CLASS_ID, value).apply()

    fun getCharacterClassId(): Int =
        prefs.getInt(CHARACTER_CLASS_ID, 1)

    fun setSkillId(value: Int) =
        prefs.edit().putInt(SKILL_ID, value).apply()

    fun getSkillId(): Int =
        prefs.getInt(SKILL_ID, 1)

    fun writeLong(key: String, value: Long) =
        prefs.edit().putLong(key, value).apply()

    fun readLong(key: String): Long =
        prefs.getLong(key, 0L)

    companion object {
        const val CHARACTER_CLASS_ID: String = "CHARACTER_CLASS_ID"
        const val SKILL_ID: String = "SKILL_ID"
    }
}