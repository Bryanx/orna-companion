package nl.bryanderidder.ornaguide.item.persistence

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Request body used for fetching data from API
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
data class ItemAssessRequestBody(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "level") var level: Int? = null,
    @Json(name = "hp") var hp: Int? = null,
    @Json(name = "mana") var mana: Int? = null,
    @Json(name = "attack") var attack: Int? = null,
    @Json(name = "magic") var magic: Int? = null,
    @Json(name = "defense") var defense: Int? = null,
    @Json(name = "resistance") var resistance: Int? = null,
    @Json(name = "dexterity") var dexterity: Int? = null,
)