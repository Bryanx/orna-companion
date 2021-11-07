package nl.bryanderidder.ornaguide.npc.model

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX

@Entity
@JsonClass(generateAdapter = true)
data class Npc(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "description") val description: String = "",
    @Json(name = "image") val image: String = "",
    @Json(name = "quests") val quests: List<Quest> = listOf()
) {

    @Ignore val previewImageUrl: String = ORNA_IMAGE_PREFIX + image

    @JsonClass(generateAdapter = true)
    data class Quest(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = "",
        @Json(name = "tier") val tier: Int = 0
    )

    companion object {
        const val NAME = "Npc"
    }
}

@Entity
@Fts4(contentEntity = Npc::class)
data class NpcFTS(
    val name: String,
)