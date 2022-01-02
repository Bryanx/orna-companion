package nl.bryanderidder.ornaguide.monster.model

import android.text.SpannableStringBuilder
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX
import nl.bryanderidder.ornaguide.shared.util.makeBold

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
    @Json(name = "immune_to") val immuneTo: List<String> = listOf(),
    @Json(name = "immune_to_status") val immuneToStatus: List<String> = listOf(),
    @Json(name = "vulnerable_to_status") val vulnerableToStatus: List<String> = listOf(),
    @Json(name = "weak_to") val weakTo: List<String> = listOf(),
    @Json(name = "drops") val drops: List<IdNamePair> = listOf(),
    @Json(name = "skills") val skills: List<IdNamePair> = listOf(),
    @Json(name = "buffs") val buffs: List<IdNamePair> = listOf(),
    @Json(name = "quests") val quests: List<IdNamePair> = listOf()
) {

    @Ignore val previewImageUrl: String = ORNA_IMAGE_PREFIX + image
    @Ignore var isFiltered: Boolean = true

    @JsonClass(generateAdapter = true)
    data class IdNamePair(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )

    @Ignore fun boldFormattedSpawns(): SpannableStringBuilder =
        formattedSpawns().makeBold("Spawn:")

    @Ignore fun formattedSpawns(): String =
        if (spawns.isEmpty())
            ""
        else
            "Spawn: ${spawns.joinToString(", ")}"

    @Ignore fun searchFormat(): String =
        if (boss)
            "BOSS"
        else
            formattedSpawns()

    @Ignore fun formattedStatusImmunities(): SpannableStringBuilder =
        if (immuneToStatus.isEmpty())
            SpannableStringBuilder()
        else
            "Status Immunity: ${immuneToStatus.joinToString(", ")}".makeBold("Status Immunity:")

    @Ignore fun formattedStatusVulnerabilities(): SpannableStringBuilder =
        if (vulnerableToStatus.isEmpty())
            SpannableStringBuilder()
        else
            "Status Vulnerability: ${vulnerableToStatus.joinToString(", ")}".makeBold("Status Vulnerability:")

    companion object {
        const val NAME = "Monster"
    }
}

@Entity
@Fts4(contentEntity = Monster::class)
data class MonsterFTS(
    val name: String,
)