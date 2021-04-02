package nl.bryanderidder.ornaguide.monster.ui.list.filter

import nl.bryanderidder.ornaguide.monster.model.Monster


/**
 * Pojo for monster filter
 * @author Bryan de Ridder
 */
data class MonsterFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
    var spawns: List<String> = listOf(),
) {
    fun applyFilter(list: List<Monster>): List<Monster> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { monster -> tiers.contains(monster.tier) }
        if (types.isNotEmpty() && !types.contains("Normal"))
            newList = newList.filter { it.boss }
        if (types.isNotEmpty() && !types.contains("Boss"))
            newList = newList.filter { !it.boss }
        if (spawns.isNotEmpty())
            newList = newList.filter { monster -> spawns.any(monster.spawns.toString()::contains) }
        return newList
    }
}