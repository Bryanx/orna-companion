package nl.bryanderidder.ornaguide.skill.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Skill(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "bought") val bought: Boolean = false,
    @Json(name = "gives") val gives: List<String> = listOf(),
    @Json(name = "causes") val causes: List<String> = listOf(),
    @Json(name = "description") val description: String = "",
    @Json(name = "element") val element: String = "",
    @Json(name = "is_magic") val isMagic: Boolean = false,
    @Json(name = "learned_by") val learnedBy: List<LearnedBy> = listOf(),
    @Json(name = "mana_cost") val manaCost: Int = 0,
    @Json(name = "monsters_use") val monstersUse: List<MonstersUse> = listOf(),
    @Json(name = "name") val name: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "type") val type: String = ""
) {
    @JsonClass(generateAdapter = true)
    data class LearnedBy(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "level") val level: Int = 0,
        @Json(name = "name") val name: String = "",
        @Json(name = "specialization") val specialization: Boolean = false
    )

    @JsonClass(generateAdapter = true)
    data class MonstersUse(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )
}