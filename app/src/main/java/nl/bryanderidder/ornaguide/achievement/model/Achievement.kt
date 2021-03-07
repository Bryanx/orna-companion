package nl.bryanderidder.ornaguide.achievement.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.NumberUtil

@Entity
@JsonClass(generateAdapter = true)
data class Achievement(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "requirement") val requirement: String = "",
    @Json(name = "reward") val reward: Int = 0
) {
    @Ignore fun formattedReward(): String {
        return "Reward: ${NumberUtil.formatNumber(reward)} orns"
    }
}