package nl.bryanderidder.ornaguide.specialization.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX

@Entity
@JsonClass(generateAdapter = true)
data class Specialization(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "female_name") val femaleName: String = "",
    @Json(name = "cost") val cost: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "images") val images: List<String> = listOf(),
    @Json(name = "skills") @Embedded val skills: Skills = Skills(),
    @Json(name = "boosts") val boosts: List<Boost> = listOf()
) {
    @Ignore val imageUrls: List<String> = images.map { ORNA_IMAGE_PREFIX + it }.toList()
    @Ignore val previewImageUrl: String = imageUrls.last()

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
}