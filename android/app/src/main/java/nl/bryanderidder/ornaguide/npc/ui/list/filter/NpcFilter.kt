package nl.bryanderidder.ornaguide.npc.ui.list.filter

import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for npc filter
 * @author Bryan de Ridder
 */
data class NpcFilter(
    var tiers: List<Int> = listOf(),
) {
    fun applyFilter(list: List<Npc>): List<Npc> {
        return if (isEmpty())
            list.forEachApply { it.isFiltered = true }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<Npc>): List<Npc> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        return newList
    }

    fun countFilterResults(list: List<Npc>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String = tiers.size.toString()

    fun isEmpty(): Boolean = tiers.isEmpty()
}