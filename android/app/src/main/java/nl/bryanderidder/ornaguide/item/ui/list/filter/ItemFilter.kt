package nl.bryanderidder.ornaguide.item.ui.list.filter

import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for item filter
 * @author Bryan de Ridder
 */
data class ItemFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
    var elements: List<String> = listOf(),
    var equippedByList: List<String> = listOf(),
    var stats: List<String> = listOf(),
    var gives: List<String> = listOf(),
    var immunities: List<String> = listOf(),
    var cures: List<String> = listOf(),
) {
    fun applyFilter(list: List<Item>): List<Item> {
        return if (isEmpty())
            list.forEachApply { it.isFiltered = true }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<Item>): List<Item> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { item -> tiers.contains(item.tier) }
        if (types.isNotEmpty())
            newList = newList.filter { item -> types.contains(item.type) }
        if (elements.isNotEmpty())
            newList = newList.filter { item -> elements.contains(item.element) }
        if (equippedByList.isNotEmpty())
            newList = newList.filter { item -> equippedByList.any(item.equippedBy.toString()::contains) }
        if (stats.isNotEmpty())
            newList = newList.filter { item -> stats.any(item.statsAsMap()::containsKey) }
        if (gives.isNotEmpty())
            newList = newList.filter { item -> gives.any(item.gives::contains) }
        if (immunities.isNotEmpty())
            newList = newList.filter { item -> immunities.any(item.immunities::contains) }
        if (cures.isNotEmpty())
            newList = newList.filter { item -> cures.any(item.cures::contains) }
        return newList
    }

    fun countFilterResults(list: List<Item>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String = getFilters().map { it.size }.sum().toString()

    fun isEmpty(): Boolean = getFilters().all { it.isEmpty() }

    fun getFilters() = listOf(
        tiers,
        types,
        elements,
        equippedByList,
        stats,
        gives,
        immunities,
        cures,
    )
}