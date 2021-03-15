package nl.bryanderidder.ornaguide.item.ui.list.filter


/**
 * Pojo for item filter
 * @author Bryan de Ridder
 */
data class ItemFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
    var elements: List<String> = listOf(),
    var equippedByList: List<String> = listOf()
)