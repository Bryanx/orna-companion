package nl.bryanderidder.ornaguide.skill.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.NumberUtil

@Entity
@JsonClass(generateAdapter = true)
data class Skill(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "bought") val bought: Boolean = false,
    @Json(name = "cost") val cost: Long = 0,
    @Json(name = "gives") val gives: List<String> = listOf(),
    @Json(name = "causes") val causes: List<String> = listOf(),
    @Json(name = "description") val description: String = "",
    @Json(name = "element") val element: String = "",
    @Json(name = "is_magic") val isMagic: Boolean = false,
    @Json(name = "learned_by") val learnedBy: List<LearnedBy> = listOf(),
    @Json(name = "mana_cost") val manaCost: Int = 0,
    @Json(name = "monsters_use") val monstersUse: List<IdNamePair> = listOf(),
    @Json(name = "pets_use") val petsUse: List<IdNamePair> = listOf(),
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
    data class IdNamePair(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )

    @Ignore
    fun formattedGives(): String = "Gives:\n" + gives.joinToString("\n")

    @Ignore
    fun formattedCauses(): String = "Causes:\n" + causes.joinToString("\n")

    @Ignore
    fun formattedCost(): String = NumberUtil.formatNumber(cost)

    @Ignore
    fun formattedTypeAndMana(): String = type + if (manaCost == 0) "" else " - $manaCost\u00A0mana"
}