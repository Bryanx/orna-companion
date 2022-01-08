package nl.bryanderidder.ornaguide.achievement.ui.list.filter

import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for achievement filter
 * @author Bryan de Ridder
 */
data class AchievementFilter(
    var tiers: List<Int> = listOf(),
) {
    fun applyFilter(list: List<Achievement>): List<Achievement> {
        return if (isEmpty())
            list.forEachApply { it.isFiltered = true }
        else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<Achievement>): List<Achievement> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        return newList
    }

    fun countFilterResults(list: List<Achievement>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String = getFilters().map { it.size }.sum().toString()

    fun isEmpty(): Boolean = getFilters().all { it.isEmpty() }

    fun getFilters() = listOf(
        tiers,
    )
}