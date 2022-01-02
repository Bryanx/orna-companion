package nl.bryanderidder.ornaguide.save.ui.filter

import nl.bryanderidder.ornaguide.save.model.Save
import nl.bryanderidder.ornaguide.shared.util.forEachApply

/**
 * Pojo for save filter
 * @author Bryan de Ridder
 */
data class SaveFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
) {
    fun applyFilter(list: List<Save>): List<Save> {
        return if (isEmpty())
            list.forEachApply { it.isFiltered = true }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<Save>): List<Save> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        if (types.isNotEmpty())
            newList = newList.filter { types.contains(it.type) }
        return newList
    }

    fun countFilterResults(list: List<Save>?): Int =
        filterList(list ?: listOf()).count()

    fun isEmpty(): Boolean = tiers.isEmpty()
}