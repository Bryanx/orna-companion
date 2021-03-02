package nl.bryanderidder.ornaguide.pet.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
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

    @JsonClass(generateAdapter = true)
    data class Stats(
        @Json(name = "spell") val spell: Int = 0,
        @Json(name = "heal") val heal: Int = 0,
        @Json(name = "buff") val buff: Int = 0
    )

    @JsonClass(generateAdapter = true)
    data class Skill(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )
}