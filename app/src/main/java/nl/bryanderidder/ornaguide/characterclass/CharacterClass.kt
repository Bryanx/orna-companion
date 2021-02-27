package nl.bryanderidder.ornaguide.characterclass

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterClass(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "female_name") val femaleName: String = "",
    @Json(name = "cost") val cost: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "images") val images: List<String> = listOf(),
    @Json(name = "learns") val learns: String = "",
    @Json(name = "skills") val skills: Skills,
    @Json(name = "tier") val tier: Int = 0
)

@JsonClass(generateAdapter = true)
data class Skills(
    @Json(name = "learns") val learns: List<Learn> = listOf(),
    @Json(name = "passives") val passives: List<Passive> = listOf(),
    @Json(name = "slots") val slots: Int = 0
)

@JsonClass(generateAdapter = true)
data class Learn(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "level") val level: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "type") val type: String = ""
)

@JsonClass(generateAdapter = true)
data class Passive(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String = ""
)