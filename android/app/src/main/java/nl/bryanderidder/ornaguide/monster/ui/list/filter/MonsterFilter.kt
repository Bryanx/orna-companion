package nl.bryanderidder.ornaguide.monster.ui.list.filter

import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for monster filter
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
data class MonsterFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
    var spawns: List<String> = listOf(),
    var weakTos: List<String> = listOf(),
    var resistantTos: List<String> = listOf(),
    var immuneTos: List<String> = listOf(),
    var immuneToStatuses: List<String> = listOf(),
    var vulnerableToStatuses: List<String> = listOf(),
) {
    fun applyFilter(list: List<Monster>): List<Monster> {
        return if (isEmpty()) {
            list.forEachApply { it.isFiltered = true }
        }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    private fun filterList(list: List<Monster>): List<Monster> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { monster -> tiers.contains(monster.tier) }
        if (types.isNotEmpty() && !types.contains("Normal"))
            newList = newList.filter { it.boss }
        if (types.isNotEmpty() && !types.contains("Boss"))
            newList = newList.filter { !it.boss }
        if (spawns.isNotEmpty())
            newList = newList.filter { monster -> spawns.any(monster.spawns.toString()::contains) }
        if (weakTos.isNotEmpty())
            newList = newList.filter { monster -> weakTos.any(monster.weakTo::contains) }
        if (resistantTos.isNotEmpty())
            newList = newList.filter { monster -> resistantTos.any(monster.resistantTo::contains) }
        if (immuneTos.isNotEmpty())
            newList = newList.filter { monster -> immuneTos.any(monster.immuneTo::contains) }
        if (immuneToStatuses.isNotEmpty())
            newList = newList.filter { monster -> immuneToStatuses.any(monster.immuneToStatus::contains) }
        if (vulnerableToStatuses.isNotEmpty())
            newList = newList.filter { monster -> vulnerableToStatuses.any(monster.vulnerableToStatus::contains) }
        return newList
    }

    fun countFilterResults(list: List<Monster>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String = getFilters().map { it.size }.sum().toString()

    fun isEmpty(): Boolean = getFilters().all { it.isEmpty() }

    fun getFilters() = listOf(
        tiers,
        types,
        spawns,
        weakTos,
        resistantTos,
        immuneTos,
        immuneToStatuses,
        vulnerableToStatuses
    )
}