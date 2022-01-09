package nl.bryanderidder.ornaguide.item.model

import android.text.SpannableStringBuilder
import androidx.room.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.shared.util.ORNA_IMAGE_PREFIX
import nl.bryanderidder.ornaguide.shared.util.makeBold

@Entity
@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "id") @PrimaryKey val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "description") val description: String? = "",
    @Json(name = "type") val type: String = "",
    @Json(name = "element") val element: String = "",
    @Json(name = "tier") val tier: Int = 0,
    @Json(name = "boss") val boss: Boolean = false,
    @Json(name = "arena") val arena: Boolean = false,
    @Json(name = "image") val image: String = "",
    @Json(name = "stats") @Embedded val stats: Stats = Stats(),
    @Json(name = "materials") val materials: List<IdNamePair> = listOf(),
    @Json(name = "dropped_by") val droppedBy: List<IdNamePair> = listOf(),
    @Json(name = "equipped_by") val equippedBy: List<IdNamePair> = listOf(),
    @Json(name = "quests") val quests: List<IdNamePair> = listOf(),
    @Json(name = "prevents") val immunities: List<String> = listOf(),
    @Json(name = "gives") val gives: List<String> = listOf(),
    @Json(name = "causes") val causes: List<String> = listOf(),
    @Json(name = "cures") val cures: List<String> = listOf(),
    @Json(name = "view_distance") val viewDistance: Boolean = false,
) {

    @Ignore
    val previewImageUrl: String = ORNA_IMAGE_PREFIX + image
    @Ignore var isFiltered: Boolean = true

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
        @Json(name = "crit") @Embedded val crit: CritStatValue = CritStatValue()
    ) {
        @JsonClass(generateAdapter = true)
        data class AttackStatValue(
            @Json(name = "base") @ColumnInfo(name = "attack") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class DefenseStatValue(
            @Json(name = "base") @ColumnInfo(name = "defense") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class DexterityStatValue(
            @Json(name = "base") @ColumnInfo(name = "dexterity") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class HpStatValue(
            @Json(name = "base") @ColumnInfo(name = "hp") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class MagicStatValue(
            @Json(name = "base") @ColumnInfo(name = "magic") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class ManaStatValue(
            @Json(name = "base") @ColumnInfo(name = "mana") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class ResistanceStatValue(
            @Json(name = "base") @ColumnInfo(name = "resistance") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class WardStatValue(
            @Json(name = "base") @ColumnInfo(name = "ward") val base: Int = 0
        )

        @JsonClass(generateAdapter = true)
        data class CritStatValue(
            @Json(name = "base") @ColumnInfo(name = "crit") val base: Int = 0
        )
    }

    @JsonClass(generateAdapter = true)
    data class IdNamePair(
        @Json(name = "id") val id: Int = 0,
        @Json(name = "name") val name: String = ""
    )

    @Ignore
    fun droppedByIncludingArena(): List<IdNamePair> =
        if (arena)
            droppedBy + IdNamePair(0, "Arena")
        else
            droppedBy

    @Ignore
    fun formattedMaterialsLabel(): String =
        if (type == "Material")
            "Material for:"
        else
            "Materials:"

    @Ignore
    fun formattedStats(): String =
        statsAsMap().map { (k, v) -> "$k:\u00A0$v" }.toList().joinToString("   ")

    @Ignore
    fun formattedImmunities(): String = immunities.joinToString("\n")

    @Ignore
    fun formattedGives(): String = gives.joinToString("\n")

    @Ignore
    fun formattedCauses(): String = causes.joinToString("\n")

    @Ignore
    fun formattedCures(): SpannableStringBuilder =
        if (cures.isEmpty())
            SpannableStringBuilder()
        else
            "Cures: ${cures.joinToString(", ")}".makeBold("Cures:")

    @Ignore
    fun statsAsMap(): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        if (stats.attack.base != 0) map["Att"] = stats.attack.base
        if (stats.defense.base != 0) map["Def"] = stats.defense.base
        if (stats.dexterity.base != 0) map["Dex"] = stats.dexterity.base
        if (stats.hp.base != 0) map["HP"] = stats.hp.base
        if (stats.magic.base != 0) map["Mag"] = stats.magic.base
        if (stats.mana.base != 0) map["Mana"] = stats.mana.base
        if (stats.resistance.base != 0) map["Res"] = stats.resistance.base
        if (stats.ward.base != 0) map["Ward"] = stats.ward.base
        if (stats.crit.base != 0) map["Crit"] = stats.crit.base
        return map
    }

    @Ignore
    val equipmentTypes = listOf("Armor", "Head", "Legs", "Weapon", "Adornment", "Off-hand", "Accessory")

    @Ignore
    fun isEquipmentType(): Boolean = equipmentTypes.contains(type)

    companion object {
        const val NAME = "Item"
        val STATS = listOf("Att", "Crit", "Def", "Dex", "HP", "Mag", "Mana", "Res", "Ward")
    }
}

@Entity
@Fts4(contentEntity = Item::class)
data class ItemFTS(
    val name: String,
)