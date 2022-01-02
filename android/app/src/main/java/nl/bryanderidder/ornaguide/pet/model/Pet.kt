package nl.bryanderidder.ornaguide.pet.model

import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.NumberUtil
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX

@Entity
@JsonClass(generateAdapter = true)
data class Pet(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "image") val image: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "cost") val cost: Int = 0,
    @Json(name = "stats") @Embedded val stats: Stats = Stats(),
    @Json(name = "skills") val skills: List<Skill> = listOf()
) {
    @Ignore val previewImageUrl: String = ORNA_IMAGE_PREFIX + image
    @Ignore var isFiltered: Boolean = true

    @JsonClass(generateAdapter = true)
    data class Stats(
        @Json(name = "attack") val attack: Int = 0,
        @Json(name = "buff") val buff: Int = 0,
        @Json(name = "debuff") val deBuff: Int = 0,
        @Json(name = "protect") val protect: Int = 0,
        @Json(name = "spell") val spell: Int = 0,
        @Json(name = "heal") val heal: Int = 0,
    )

    @JsonClass(generateAdapter = true)
    data class Skill(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )

    @Ignore
    fun formattedCost(): String =
        if (tier > 7)
            "${NumberUtil.formatNumber(cost)} orns"
        else
            "${NumberUtil.formatNumber(cost)} gold"

    @Ignore
    fun formattedStats(): String {
        val formattedStats = mutableListOf<String>()
        if (stats.attack != 0) formattedStats += "Attack chance ${stats.attack}%"
        if (stats.buff != 0) formattedStats += "Buff chance ${stats.buff}%"
        if (stats.deBuff != 0) formattedStats += "Debuff chance ${stats.deBuff}%"
        if (stats.protect != 0) formattedStats += "Protect chance ${stats.protect}%"
        if (stats.spell != 0) formattedStats += "Spell chance ${stats.spell}%"
        if (stats.heal != 0) formattedStats += "Heal chance ${stats.heal}%"
        return formattedStats.joinToString("\n")
    }

    companion object {
        const val NAME = "Pet"
    }
}

@Entity
@Fts4(contentEntity = Pet::class)
data class PetFTS(
    val name: String,
)