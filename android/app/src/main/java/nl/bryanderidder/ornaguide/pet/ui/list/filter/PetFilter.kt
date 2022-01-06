package nl.bryanderidder.ornaguide.pet.ui.list.filter

import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for pet filter
 * @author Bryan de Ridder
 */
data class PetFilter(
    var tiers: List<Int> = listOf(),
    var stats: List<String> = listOf(),
) {
    fun applyFilter(list: List<Pet>): List<Pet> {
        return if (isEmpty())
            list.forEachApply { it.isFiltered = true }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<Pet>): List<Pet> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        if (stats.isNotEmpty())
            newList = newList.filter { stats.any(it.getActiveStats()::containsKey) }
        return newList
    }

    fun countFilterResults(list: List<Pet>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String = (tiers.size + stats.size).toString()

    fun isEmpty(): Boolean = tiers.isEmpty() && stats.isEmpty()
}