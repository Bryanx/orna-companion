package nl.bryanderidder.ornaguide.specialization.ui.list.filter

import nl.bryanderidder.ornaguide.shared.util.forEachApply
import nl.bryanderidder.ornaguide.specialization.model.Specialization


/**
 * Pojo for specialization filter
 * @author Bryan de Ridder
 */
data class SpecializationFilter(
    var tiers: List<Int> = listOf(),
    var boosts: List<String> = listOf(),
) {
    fun applyFilter(list: List<Specialization>): List<Specialization> {
        return if (isEmpty())
            list.forEachApply { it.isFiltered = true }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<Specialization>): List<Specialization> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        if (boosts.isNotEmpty())
            newList = newList.filter { spec -> spec.boosts
                .filter { it.value > 0 }
                .map { it.formattedName() }
                .any { boosts.contains(it) }
            }
        return newList
    }

    fun countFilterResults(list: List<Specialization>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String = (tiers.size + boosts.size).toString()

    fun isEmpty(): Boolean = tiers.isEmpty() && boosts.isEmpty()
}