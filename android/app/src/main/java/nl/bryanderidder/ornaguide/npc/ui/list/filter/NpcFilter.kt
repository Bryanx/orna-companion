package nl.bryanderidder.ornaguide.npc.ui.list.filter

import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for npc filter
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
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

    fun filterCount(): String = getFilters().map { it.size }.sum().toString()

    fun isEmpty(): Boolean = getFilters().all { it.isEmpty() }

    fun getFilters() = listOf(
        tiers,
    )
}