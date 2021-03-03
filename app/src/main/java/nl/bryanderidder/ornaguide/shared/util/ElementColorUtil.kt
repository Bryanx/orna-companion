package nl.bryanderidder.ornaguide.shared.util

import nl.bryanderidder.ornaguide.R

object ElementColorUtil {
    fun getColorForElement(element: String): Int {
        return when (element) {
            "Dark" -> R.color.elementDarkColor
            "Water" -> R.color.elementWaterColor
            "Holy" -> R.color.elementHolyColor
            "Earthen" -> R.color.elementEarthenColor
            "Lightning" -> R.color.elementLightningColor
            "Fire" -> R.color.elementFireColor
            else -> R.color.textColorHeader
        }
    }
}