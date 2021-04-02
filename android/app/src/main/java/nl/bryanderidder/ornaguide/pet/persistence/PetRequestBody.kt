package nl.bryanderidder.ornaguide.pet.persistence

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Request body used for fetching data from API
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
data class PetRequestBody(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "tier") val tier: Int? = null,
    @Json(name = "attack") val attack: Int? = null,
    @Json(name = "spell") val spell: Int? = null,
    @Json(name = "heal") val heal: Int? = null,
    @Json(name = "buff") val buff: Int? = null,
    @Json(name = "debuff") val deBuff: Int? = null,
)