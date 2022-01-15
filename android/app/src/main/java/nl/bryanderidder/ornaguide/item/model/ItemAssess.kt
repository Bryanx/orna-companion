package nl.bryanderidder.ornaguide.item.model

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.core.text.color
import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.getPlusOrMinusColor
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity
@JsonClass(generateAdapter = true)
data class ItemAssess(
    @Transient @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Json(name = "id") @ColumnInfo(name = "itemId") val itemId: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "quality") val quality: String = "",
    @Json(name = "stats") @Embedded val stats: Stats = Stats(),
    var requestBody: String = "",
    val creationDate: Long = Instant.now().toEpochMilli(),
    @ColumnInfo(name = "itemImage") var itemImage: String = "",
) {
    @Ignore
    val previewImageUrl: String = ORNA_IMAGE_PREFIX + itemImage

    @JsonClass(generateAdapter = true)
    data class Stats(
        @Json(name = "attack") @Embedded val attack: AttackStatValue = AttackStatValue(),
        @Json(name = "defense") @Embedded val defense: DefenseStatValue = DefenseStatValue(),
        @Json(name = "dexterity") @Embedded val dexterity: DexterityStatValue = DexterityStatValue(),
        @Json(name = "hp") @Embedded val hp: HpStatValue = HpStatValue(),
        @Json(name = "magic") @Embedded val magic: MagicStatValue = MagicStatValue(),
        @Json(name = "mana") @Embedded val mana: ManaStatValue = ManaStatValue(),
        @Json(name = "resistance") @Embedded val resistance: ResistanceStatValue = ResistanceStatValue(),
        @Json(name = "ward") @Embedded val ward: WardStatValue = WardStatValue(),
        @Json(name = "crit") @Embedded val crit: CritStatValue = CritStatValue(),
    ) {
        open class StatValue(
            open val base: Int = 0,
            open val values: List<Int> = listOf(),
        )

        @JsonClass(generateAdapter = true)
        data class AttackStatValue(
            @Json(name = "base") @ColumnInfo(name = "attackBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "attackValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class DefenseStatValue(
            @Json(name = "base") @ColumnInfo(name = "defenseBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "defenseValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class DexterityStatValue(
            @Json(name = "base") @ColumnInfo(name = "dexterityBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "dexterityValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class HpStatValue(
            @Json(name = "base") @ColumnInfo(name = "hpBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "hpValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class MagicStatValue(
            @Json(name = "base") @ColumnInfo(name = "magicBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "magicValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class ManaStatValue(
            @Json(name = "base") @ColumnInfo(name = "manaBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "manaValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class ResistanceStatValue(
            @Json(name = "base") @ColumnInfo(name = "resistanceBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "resistanceValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class WardStatValue(
            @Json(name = "base") @ColumnInfo(name = "wardBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "wardValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)

        @JsonClass(generateAdapter = true)
        data class CritStatValue(
            @Json(name = "base") @ColumnInfo(name = "critBase") override val base: Int = 0,
            @Json(name = "values") @ColumnInfo(name = "critValues") override val values: List<Int> = listOf(),
        ) : StatValue(base, values)
    }

    fun formattedCreationDate(): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(creationDate),
            TimeZone.getDefault().toZoneId())
        return formatter.format(dateTime)
    }

    fun formattedQuality(): String {
        if (quality.isEmpty())
            return "0%"
        return "${(quality.toFloat() * 100).toInt()}%"
    }

    fun formattedQualityType(): String {
        if (quality.isEmpty()) return ""
        val qualityPercent = (quality.toFloat() * 100).toInt()
        return when {
            qualityPercent < 100 -> "Broken/Poor"
            qualityPercent < 110 -> "Common"
            qualityPercent < 121 -> "Superior"
            qualityPercent < 140 -> "Famed"
            qualityPercent < 171 -> "Legendary"
            else -> "Ornate"
        }
    }

    fun report(context: Context): SpannableStringBuilder {
        val builder = SpannableStringBuilder()
            .color(getQualityColor(context)) { append("Quality ${formattedQuality()}\n${formattedQualityType()}") }
            .append("\n\n")
        val levels = mutableListOf("\n\nLevel 1", "\n\nLevel 2", "\n\nLevel 3", "\n\nLevel 4", "\n\nLevel 5", "\n\nLevel 6", "\n\nLevel 7", "\n\nLevel 8", "\n\nLevel 9", "\n\nLevel 10", "\n\nMasterforged", "\n\nDemonforged", "Godforged").reversed()
        val statMap = listOf("Att" to stats.attack, "Def" to stats.defense, "Dex" to stats.dexterity, "Hp" to stats.hp, "Mag" to stats.magic, "Mana" to stats.mana, "Res" to stats.resistance, "Ward" to stats.ward, "Crit" to stats.crit).filter { (_, stat) -> stat.base != 0 }
        levels.forEachIndexed { i, level ->
            when {
                level.contains("Masterforged") -> builder.bold { color(context.color(R.color.masterForged)) { append(level) } }
                level.contains("Demonforged") -> builder.bold { color(context.color(R.color.demonForged)) { append(level) } }
                level.contains("Godforged") -> builder.bold { color(context.color(R.color.godForged)) { append(level) } }
                else -> builder.bold { append(level) }
            }
            builder.append("\n")
            statMap.forEach { stat ->
                val value = stat.second.values.reversed()[i]
                builder.append("${stat.first}:")
                    .color(context.getPlusOrMinusColor(value)) { append("$value") }
                    .append("  ")
            }
        }
        return builder
    }

    fun formattedDate(): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(creationDate),
            TimeZone.getDefault().toZoneId())
        return formatter.format(dateTime)
    }

    @Ignore
    fun getQualityColor(context: Context): Int {
        if (quality.isEmpty()) return 0
        val qualityPercent = (quality.toFloat() * 100).toInt()
        return when {
            qualityPercent < 100 -> context.color(R.color.itemCategoryBroken)
            qualityPercent < 110 -> context.color(R.color.itemCategoryCommon)
            qualityPercent < 121 -> context.color(R.color.itemCategorySuperior)
            qualityPercent < 140 -> context.color(R.color.itemCategoryFamed)
            qualityPercent < 171 -> context.color(R.color.itemCategoryLegendary)
            else -> context.color(R.color.itemCategoryOrnate)
        }
    }

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    }
}