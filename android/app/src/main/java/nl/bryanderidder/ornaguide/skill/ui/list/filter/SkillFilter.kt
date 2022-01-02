package nl.bryanderidder.ornaguide.skill.ui.list.filter

import nl.bryanderidder.ornaguide.shared.util.forEachApply
import nl.bryanderidder.ornaguide.skill.model.Skill


/**
 * Pojo for skill filter
 * @author Bryan de Ridder
 */
data class SkillFilter(
    var tiers: List<Int> = listOf(),
    var types: List<String> = listOf(),
    var elements: List<String> = listOf(),
) {
    fun applyFilter(list: List<Skill>): List<Skill> {
        return if (isEmpty())
            list.forEachApply { it.isFiltered = true }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<Skill>): List<Skill> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { skill -> tiers.contains(skill.tier) }
        if (types.isNotEmpty())
            newList = newList.filter { skill -> types.contains(skill.type) }
        if (elements.isNotEmpty())
            newList = newList.filter { skill -> elements.contains(skill.element) }
        return newList
    }

    fun countFilterResults(list: List<Skill>?): Int =
        filterList(list ?: listOf()).count()

    fun isEmpty(): Boolean = tiers.isEmpty()
}