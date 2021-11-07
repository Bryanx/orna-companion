package nl.bryanderidder.ornaguide.save.ui.filter

import nl.bryanderidder.ornaguide.save.model.Save

/**
 * Pojo for save filter
 * @author Bryan de Ridder
 */
data class SaveFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
) {
    fun applyFilter(list: List<Save>): List<Save> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        if (types.isNotEmpty())
            newList = newList.filter { types.contains(it.type) }
        return newList
    }
}