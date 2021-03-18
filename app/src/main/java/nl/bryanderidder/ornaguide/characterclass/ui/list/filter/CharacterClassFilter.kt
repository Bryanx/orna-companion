package nl.bryanderidder.ornaguide.characterclass.ui.list.filter

import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass


/**
 * Pojo for character class filter
 * @author Bryan de Ridder
 */
data class CharacterClassFilter(
    var tiers: List<Int> = listOf(),
    var costTypes: List<String> = listOf(),
) {
    fun applyFilter(list: List<CharacterClass>): List<CharacterClass> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        if (costTypes.isNotEmpty() && !costTypes.contains("Orns"))
            newList = newList.filter { it.cost.contains("$") }
        if (costTypes.isNotEmpty() && !costTypes.contains("Money"))
            newList = newList.filter { it.cost.contains("orns") }
        return newList
    }
}