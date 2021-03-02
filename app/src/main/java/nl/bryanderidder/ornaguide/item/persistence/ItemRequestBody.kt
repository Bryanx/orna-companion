package nl.bryanderidder.ornaguide.item.persistence

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Request body used for fetching data from API
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
data class ItemRequestBody(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "tier") val tier: Int? = null,
    @Json(name = "type") val cost: String? = null,
    @Json(name = "boss") val boss: Boolean? = null,
    @Json(name = "hp") val hp: Int? = null,
    @Json(name = "mana") val mana: Int? = null,
    @Json(name = "attack") val attack: Int? = null,
    @Json(name = "magic") val magic: Int? = null,
    @Json(name = "defense") val defense: Int? = null,
    @Json(name = "resistance") val resistance: Int? = null,
    @Json(name = "dexterity") val dexterity: Int? = null,
    @Json(name = "element") val element: String? = null,
    @Json(name = "equipped_by") val equippedBy: String? = null,
)