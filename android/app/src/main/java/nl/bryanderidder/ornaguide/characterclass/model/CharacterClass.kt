package nl.bryanderidder.ornaguide.characterclass.model

import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX

@Entity
@JsonClass(generateAdapter = true)
data class CharacterClass(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "female_name") val femaleName: String = "",
    @Json(name = "cost") val cost: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "images") val images: List<String> = listOf(),
    @Json(name = "learns") val learns: String = "",
    @Json(name = "skills") @Embedded val skills: Skills = Skills(),
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "stats") val stats: List<String> = listOf(),
    @Json(name = "preferred_weapons") val preferredWeapons: List<String> = listOf(),
) {
    @Ignore val imageUrls: List<String> = images.map { ORNA_IMAGE_PREFIX + it }.toList()
    @Ignore val previewImageUrl: String = imageUrls.last()
    @Ignore var isFiltered: Boolean = true

    @JsonClass(generateAdapter = true)
    data class Skills(
        @Json(name = "learns") @ColumnInfo(name = "skillsLearned") val learns: List<Learn> = listOf(),
        @Json(name = "passives") val passives: List<Passive> = listOf(),
        @Json(name = "slots") val slots: Int = 0
    )

    @JsonClass(generateAdapter = true)
    data class Learn(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "level") val level: Int = 0,
        @Json(name = "name") val name: String = "",
        @Json(name = "type") val type: String = ""
    )

    @JsonClass(generateAdapter = true)
    data class Passive(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )

    @Ignore
    fun formattedLearns(): String = "Learns ${learns.toLowerCase()}"

    @Ignore
    fun formattedPreferredWeapons(): String = when {
        preferredWeapons.isEmpty() -> ""
        preferredWeapons.size == 1 -> "Preferred weapon: ${preferredWeapons[0]}"
        else -> "Preferred weapons: ${preferredWeapons.joinToString(", ")}"
    }

    companion object {
        const val NAME = "Class"
    }
}

@Entity
@Fts4(contentEntity = CharacterClass::class)
data class CharacterClassFTS(
    val name: String,
)