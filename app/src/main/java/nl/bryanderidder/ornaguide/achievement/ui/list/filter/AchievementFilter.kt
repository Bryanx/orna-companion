package nl.bryanderidder.ornaguide.achievement.ui.list.filter

import nl.bryanderidder.ornaguide.achievement.model.Achievement


/**
 * Pojo for achievement filter
 * @author Bryan de Ridder
 */
data class AchievementFilter(
    var tiers: List<Int> = listOf(),
) {
    fun applyFilter(list: List<Achievement>): List<Achievement> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        return newList
    }
}