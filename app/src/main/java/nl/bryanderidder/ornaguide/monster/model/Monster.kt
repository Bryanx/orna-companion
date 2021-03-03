package nl.bryanderidder.ornaguide.monster.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX

@Entity
@JsonClass(generateAdapter = true)
data class Monster(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "boss") val boss: Boolean = false,
    @Json(name = "image") val image: String = "",
    @Json(name = "level") val level: Int = 0,
    @Json(name = "spawns") val spawns: List<String> = listOf(),
    @Json(name = "resistant_to") val resistantTo: List<String> = listOf(),
    @Json(name = "drops") val drops: List<IdNamePair> = listOf(),
    @Json(name = "skills") val skills: List<IdNamePair> = listOf()
) {

    @Ignore val previewImageUrl: String = ORNA_IMAGE_PREFIX + image

    @JsonClass(generateAdapter = true)
    data class IdNamePair(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )
}