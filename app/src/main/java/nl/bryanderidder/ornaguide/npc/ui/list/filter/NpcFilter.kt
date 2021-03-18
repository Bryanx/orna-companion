package nl.bryanderidder.ornaguide.npc.ui.list.filter

import nl.bryanderidder.ornaguide.npc.model.Npc


/**
 * Pojo for npc filter
 * @author Bryan de Ridder
 */
data class NpcFilter(
    var tiers: List<Int> = listOf(),
) {
    fun applyFilter(list: List<Npc>): List<Npc> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        return newList
    }
}