package nl.bryanderidder.ornaguide.monster.ui.list.filter

import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for monster filter
 * @author Bryan de Ridder
 */
data class MonsterFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
    var spawns: List<String> = listOf(),
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
        return newList
    }

    fun countFilterResults(list: List<Monster>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String =
        (tiers.size + types.size + spawns.size).toString()

    fun isEmpty(): Boolean =
        tiers.isEmpty() && types.isEmpty() && spawns.isEmpty()
}