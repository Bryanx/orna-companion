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
        return getActiveStats()
            .map { (stat, value) -> "$stat chance $value%" }
            .joinToString("\n")
    }

    @Ignore
    fun getActiveStats(): Map<String, Int> =
        getAllStats().filter { (_, value) -> value != 0 }

    @Ignore
    fun getAllStats(): Map<String, Int> = mapOf(
        "Attack" to stats.attack,
        "Buff" to stats.buff,
        "Debuff" to stats.deBuff,
        "Protect" to stats.protect,
        "Spell" to stats.spell,
        "Heal" to stats.heal,
    )

    companion object {
        const val NAME = "Pet"
        val STATS = listOf("Attack", "Buff", "Debuff", "Protect", "Spell", "Heal")
    }
}

@Entity
@Fts4(contentEntity = Pet::class)
data class PetFTS(
    val name: String,
)