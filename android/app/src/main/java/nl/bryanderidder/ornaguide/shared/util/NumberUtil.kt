package nl.bryanderidder.ornaguide.shared.util

import java.text.DecimalFormat


/**
 * Number utility functions
 * @author Bryan de Ridder
 */
object NumberUtil {
    private val decimalFormat = DecimalFormat("###,###.###")

    fun formatNumber(number: Number): String {
        return decimalFormat.format(number)
    }
}