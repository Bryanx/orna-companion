package nl.bryanderidder.ornaguide.achievement.model

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.NumberUtil
import nl.bryanderidder.ornaguide.shared.util.ORNA_ICON_IMAGE_PREFIX

@Entity
@JsonClass(generateAdapter = true)
data class Achievement(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "requirement") val requirement: String = "",
    @Json(name = "reward") val reward: Int = 0
) {
    @Ignore
    val previewImageUrl: String = "${ORNA_ICON_IMAGE_PREFIX}trophy.png"

    @Ignore fun formattedReward(): String {
        return "Reward: ${NumberUtil.formatNumber(reward)} orns"
    }

    companion object {
        const val NAME = "Achievement"
    }
}

@Entity
@Fts4(contentEntity = Achievement::class)
data class AchievementFTS(
    val name: String,
)