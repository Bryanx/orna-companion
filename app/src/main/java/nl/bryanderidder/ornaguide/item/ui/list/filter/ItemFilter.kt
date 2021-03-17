package nl.bryanderidder.ornaguide.item.ui.list.filter

import nl.bryanderidder.ornaguide.item.model.Item


/**
 * Pojo for item filter
 * @author Bryan de Ridder
 */
data class ItemFilter(
    var tiers: List<Int>? = listOf(),
    var types: List<String>? = listOf(),
    var elements: List<String>? = listOf(),
    var equippedByList: List<String>? = listOf()
) {
    fun applyFilter(list: List<Item>): List<Item> {
        var newList = list
        if (tiers?.isNotEmpty() == true)
            newList = newList.filter { item -> tiers?.contains(item.tier) == true }
        if (types?.isNotEmpty() == true)
            newList = newList.filter { item -> types?.contains(item.type) == true }
        if (elements?.isNotEmpty() == true)
            newList = newList.filter { item -> elements?.contains(item.element) == true }
        if (equippedByList?.isNotEmpty() == true)
            newList = newList.filter { item -> equippedByList?.any(item.equippedBy.toString()::contains) == true }
        return newList
    }
}