package nl.bryanderidder.ornaguide.characterclass.persistence

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Request body used for fetching data from API
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
data class CharacterClassRequestBody(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "female_name") val femaleName: String? = null,
    @Json(name = "cost") val cost: String? = null,
    @Json(name = "slots") val slots: Int? = null,
    @Json(name = "tier") val tier: Int? = null
)