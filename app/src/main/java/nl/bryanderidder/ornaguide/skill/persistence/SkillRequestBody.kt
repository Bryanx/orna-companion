package nl.bryanderidder.ornaguide.skill.persistence

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SkillRequestBody(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "tier") val tier: Int? = null,
    @Json(name = "mana_cost") val manaCost: Int? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "element") val element: String? = null
)