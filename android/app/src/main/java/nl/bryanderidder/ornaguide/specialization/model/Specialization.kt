package nl.bryanderidder.ornaguide.specialization.model

import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.shared.util.NumberUtil
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX
import timber.log.Timber

@Entity
@JsonClass(generateAdapter = true)
data class Specialization(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "female_name") val femaleName: String = "",
    @Json(name = "cost") val cost: String = "",
    @Json(name = "price") val price: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "images") val images: List<String> = listOf(),
    @Json(name = "skills") @Embedded val skills: Skills = Skills(),
    @Json(name = "boosts") val boosts: List<Boost> = listOf(),
    @Json(name = "preferred_weapons") val preferredWeapons: List<String> = listOf(),
) {
    @Ignore val imageUrls: List<String> = images.map { ORNA_IMAGE_PREFIX + it }.toList()
    @Ignore val previewImageUrl: String = imageUrls.last()
    @Ignore var isFiltered: Boolean = true

    @JsonClass(generateAdapter = true)
    data class Skills(
        @Json(name = "passives") val passives: List<CharacterClass.Passive> = listOf(),
        @Json(name = "learns") val learns: List<CharacterClass.Learn> = listOf(),
        @Json(name = "slots") val slots: Int = 0
    )

    @JsonClass(generateAdapter = true)
    data class Boost(
        @Json(name = "name") val name: String = "",
        @Json(name = "value") val value: Int = 0
    ) {
        @Ignore
        fun formattedName(): String = when (name.toLowerCase()) {
            "hit points" -> "HP"
            "attack" -> "Att"
            "defense" -> "Def"
            "resistance" -> "Res"
            "magic" -> "Mag"
            "dexterity" -> "Dex"
            "mana" -> "Mana"
            else -> "?"
        }

        @Ignore
        fun formattedValue(): String =
            if (value >= 0)
                "+$value%"
            else
                "$value%"
    }

    @Ignore
    fun formattedCost(): String = try {
        if (cost.isEmpty() && price.isNotEmpty())
            "${NumberUtil.formatNumber(price.toDouble())} orns"
        else
            cost
    } catch (e: NumberFormatException) {
        Timber.e(e, "Couldn't format number for specialization price {\"id\":$id, \"price\": $price}")
        cost
    }

    @Ignore
    fun formattedPreferredWeapons(): String = when {
        preferredWeapons.isEmpty() -> ""
        preferredWeapons.size == 1 -> "Preferred weapon: ${preferredWeapons[0]}"
        else -> "Preferred weapons: ${preferredWeapons.joinToString(", ")}"
    }

    companion object {
        const val NAME = "Specialization"
    }
}

@Entity
@Fts4(contentEntity = Specialization::class)
data class SpecializationFTS(
    val name: String,
)