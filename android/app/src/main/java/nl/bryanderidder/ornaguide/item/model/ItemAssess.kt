package nl.bryanderidder.ornaguide.item.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemAssess(
    @Json(name = "quality") val quality: String = "",
    @Json(name = "stats") val stats: Stats = Stats(),
) {
    @JsonClass(generateAdapter = true)
    data class Stats(
            @Json(name = "attack") val attack: AttackStatValue = AttackStatValue(),
            @Json(name = "defense") val defense: DefenseStatValue = DefenseStatValue(),
            @Json(name = "dexterity") val dexterity: DexterityStatValue = DexterityStatValue(),
            @Json(name = "hp") val hp: HpStatValue = HpStatValue(),
            @Json(name = "magic") val magic: MagicStatValue = MagicStatValue(),
            @Json(name = "mana") val mana: ManaStatValue = ManaStatValue(),
            @Json(name = "resistance") val resistance: ResistanceStatValue = ResistanceStatValue(),
            @Json(name = "ward") val ward: WardStatValue = WardStatValue(),
            @Json(name = "crit") val crit: CritStatValue = CritStatValue(),
    ) {
        @JsonClass(generateAdapter = true)
        data class AttackStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class DefenseStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class DexterityStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class HpStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class MagicStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class ManaStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class ResistanceStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class WardStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class CritStatValue(
            @Json(name = "base") val base: Int = 0,
            @Json(name = "values") val values: List<Int> = listOf(),
        )

        fun formattedLevels(): String {
            val levels = mutableListOf("Level 1:", "Level 2:", "Level 3:", "Level 4:", "Level 5:", "Level 6:", "Level 7:", "Level 8:", "Level 9:", "Level 10:", "Masterforged:", "Demonforged:", "Godforged:")
            if (attack.base != 0) attack.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 att:$value" }
            if (defense.base != 0) defense.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 def:$value" }
            if (dexterity.base != 0) dexterity.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 dex:$value" }
            if (hp.base != 0) hp.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 hp:$value" }
            if (magic.base != 0) magic.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 mag:$value" }
            if (mana.base != 0) mana.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 mana:$value" }
            if (resistance.base != 0) resistance.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 res:$value" }
            if (ward.base != 0) ward.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 ward:$value" }
            if (crit.base != 0) crit.values.forEachIndexed { i, value -> levels[i] = "${levels[i]} \u0009 crit:$value" }
            return levels.joinToString("\n")
        }
    }

    fun formattedQuality():String {
        if (quality.isEmpty())
            return "0%"
        return "${(quality.toFloat() * 100).toInt()}%"
    }

}