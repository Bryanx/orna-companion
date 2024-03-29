package nl.bryanderidder.ornaguide.skill.model

import android.text.SpannableStringBuilder
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.NumberUtil
import nl.bryanderidder.ornaguide.shared.util.ORNA_ICON_IMAGE_PREFIX
import nl.bryanderidder.ornaguide.shared.util.makeBold

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
    @Json(name = "buffed_by") val buffedBy: List<IdNamePair> = listOf(),
    @Json(name = "name") val name: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "type") val type: String = "",
    @Json(name = "cures") val cures: List<String> = listOf(),
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
    val previewImageUrl: String = when {
        type.contains("Attack") -> "${ORNA_ICON_IMAGE_PREFIX}weapon.png"
        type.contains("Passive") -> "${ORNA_ICON_IMAGE_PREFIX}time.png"
        else -> "${ORNA_ICON_IMAGE_PREFIX}staff.png"
    }

    @Ignore var isFiltered: Boolean = true

    @Ignore
    fun formattedGives(): String = "Gives:\n" + gives.joinToString("\n")

    @Ignore
    fun formattedCauses(): String = "Causes:\n" + causes.joinToString("\n")

    @Ignore
    fun formattedCost(): String = NumberUtil.formatNumber(cost)

    @Ignore
    fun formattedTypeAndMana(): String = type + if (manaCost == 0) "" else " - $manaCost\u00A0mana"

    @Ignore
    fun formattedCures(): SpannableStringBuilder =
        if (cures.isEmpty())
            SpannableStringBuilder()
        else
            "Cures: ${cures.joinToString(", ")}".makeBold("Cures:")

    companion object {
        const val NAME = "Skill"
    }
}

@Entity
@Fts4(contentEntity = Skill::class)
data class SkillFTS(
    val name: String,
)