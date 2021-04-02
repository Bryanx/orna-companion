package nl.bryanderidder.ornaguide.shared.ui.element

import android.widget.TextView
import androidx.databinding.BindingAdapter
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.monster.ui.detail.ElementsAdapter
import nl.bryanderidder.ornaguide.shared.util.color

object ElementViewBinding {
    @JvmStatic
    @BindingAdapter("elementsList", requireAll = true)
    fun bindAdapterElementsList(view: ElementsRecyclerView, items: List<String>?) {
        (view.adapter as ElementsAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("elementColor")
    fun bindElementColor(view: TextView, element: String?) {
        val colorId = when (element) {
            "Dark" -> R.color.elementDarkColor
            "Water" -> R.color.elementWaterColor
            "Holy" -> R.color.elementHolyColor
            "Earthen" -> R.color.elementEarthenColor
            "Lightning" -> R.color.elementLightningColor
            "Fire" -> R.color.elementFireColor
            "Arcane" -> R.color.elementArcaneColor
            else -> R.color.textColorHeader
        }
        view.setTextColor(view.context.color(colorId))
    }
}