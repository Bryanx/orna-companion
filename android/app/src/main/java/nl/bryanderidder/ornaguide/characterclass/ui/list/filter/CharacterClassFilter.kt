package nl.bryanderidder.ornaguide.characterclass.ui.list.filter

import com.squareup.moshi.JsonClass
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.shared.util.forEachApply


/**
 * Pojo for character class filter
 * @author Bryan de Ridder
 */
@JsonClass(generateAdapter = true)
data class CharacterClassFilter(
    var tiers: List<Int> = listOf(),
    var costTypes: List<String> = listOf(),
) {
    fun applyFilter(list: List<CharacterClass>): List<CharacterClass> {
        return if (isEmpty()) {
            list.forEachApply { it.isFiltered = true }
        } else {
            val newList = filterList(list)
            list.forEachApply { it.isFiltered = newList.contains(it) }
        }
    }

    fun filterList(list: List<CharacterClass>): List<CharacterClass> {
        var newList = list
        if (tiers.isNotEmpty())
            newList = newList.filter { tiers.contains(it.tier) }
        if (costTypes.isNotEmpty() && !costTypes.contains("Orns"))
            newList = newList.filter { it.cost.contains("$") }
        if (costTypes.isNotEmpty() && !costTypes.contains("Money"))
            newList = newList.filter { it.cost.contains("orns") }
        return newList
    }

    fun countFilterResults(list: List<CharacterClass>?): Int =
        filterList(list ?: listOf()).count()

    fun filterCount(): String = getFilters().map { it.size }.sum().toString()

    fun isEmpty(): Boolean = getFilters().all { it.isEmpty() }

    fun getFilters() = listOf(
        tiers,
        costTypes,
    )
}