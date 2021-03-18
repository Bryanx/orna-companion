package nl.bryanderidder.ornaguide.specialization.ui.list.filter

import nl.bryanderidder.ornaguide.specialization.model.Specialization


/**
 * Pojo for specialization filter
 * @author Bryan de Ridder
 */
data class SpecializationFilter(
    var tiers: List<Int> = listOf(),
) {
    fun applyFilter(list: List<Specialization>): List<Specialization> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        return newList
    }
}