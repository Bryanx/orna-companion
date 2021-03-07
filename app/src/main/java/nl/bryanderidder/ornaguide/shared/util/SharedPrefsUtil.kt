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

    fun writeLong(key: String, value: Long) =
        prefs.edit().putLong(key, value).apply()

    fun readLong(key: String): Long =
        prefs.getLong(key, 0L)

    companion object {
        const val CHARACTER_CLASS_ID: String = "CHARACTER_CLASS_ID"
        const val SPECIALIZATION_ID: String = "SPECIALIZATION_ID"
        const val SKILL_ID: String = "SKILL_ID"
        const val PET_ID: String = "PET_ID"
        const val MONSTER_ID: String = "MONSTER_ID"
        const val ITEM_ID: String = "ITEM_ID"
    }
}