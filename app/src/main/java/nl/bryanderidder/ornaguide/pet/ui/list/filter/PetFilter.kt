package nl.bryanderidder.ornaguide.pet.ui.list.filter

import nl.bryanderidder.ornaguide.pet.model.Pet


/**
 * Pojo for pet filter
 * @author Bryan de Ridder
 */
data class PetFilter(
    var tiers: List<Int> = listOf(),
) {
    fun applyFilter(list: List<Pet>): List<Pet> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { pet -> tiers.contains(pet.tier) }
        return newList
    }
}