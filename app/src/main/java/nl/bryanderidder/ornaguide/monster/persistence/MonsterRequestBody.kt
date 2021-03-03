package nl.bryanderidder.ornaguide.monster.persistence

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Request body used for fetching data from API
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
data class MonsterRequestBody(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "tier") val tier: Int? = null,
    @Json(name = "boss") val boss: Boolean? = null,
    @Json(name = "spawn") val spawn: String? = null,
    @Json(name = "weak_to") val weak_to: String? = null,
    @Json(name = "resistant_to") val resistant_to: String? = null,
    @Json(name = "immune_to") val immune_to: String? = null,
    )