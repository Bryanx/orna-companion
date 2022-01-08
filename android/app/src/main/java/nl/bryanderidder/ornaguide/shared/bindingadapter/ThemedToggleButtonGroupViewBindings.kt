package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.*
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup

object ThemedToggleButtonGroupViewBindings {

    @JvmStatic
    @BindingAdapter("toggleTierButtons", "toggleTierInitiallySelectedButtons")
    fun bindToggleTierButtons(view: ThemedToggleButtonGroup, items: List<Int>?, selectedItems: List<Int>?) {
        val presentButtons = view.buttons.map(ThemedButton::text).toList()
        //create and select buttons
        items?.filter { !presentButtons.contains(it.toString()) }
            ?.forEach { item ->
                val btn = ThemedButton(view.context)
                btn.text = item.toString()
                btn.textColor = view.context.color(R.color.textColor)
                btn.selectedTextColor = view.context.color(R.color.textColorDark)
                btn.bgColor = view.context.color(R.color.cardColor)
                btn.selectedBgColor = view.context.attrColor(R.attr.colorPrimary)
                btn.applyToTexts {
                    it.layoutGravity = Gravity.CENTER
                    it.setViewMargin(left = 10.dp)
                }
                ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_star_24)?.let {
                    btn.applyToIcons { icon ->
                        icon.setImageDrawable(it)
                        icon.layoutGravity = Gravity.CENTER
                        icon.setViewMargin(right = 10.dp)
                        icon.setTint(view.context.color(R.color.textColor), PorterDuff.Mode.SRC_ATOP)
                    }
                }
                btn.ivSelectedIcon.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_star_24))
                btn.ivSelectedIcon.setTint(view.context.color(R.color.ornaGreenDark), PorterDuff.Mode.SRC_ATOP)
                view.addView(btn,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                btn.applyToCards {
                    it.setViewMargin(vertical = 6.dp)
                    it.setViewPadding(horizontal = 12.dp.toFloat(), vertical = 4.dp.toFloat())
                }
                if (selectedItems?.contains(item) == true)
                    view.selectButton(btn)
            }
        //deselect buttons
        view.buttons.filter { it.isSelected }
            .filter { selectedItems?.contains(it.text.toInt()) == false }
            .forEach { view.selectButton(it) }
    }

    @JvmStatic
    @BindingAdapter("toggleButtons", "toggleInitiallySelectedButtons")
    fun bindToggleButtons(view: ThemedToggleButtonGroup, items: List<String>?, selectedItems: List<String>?) {
        val presentButtons = view.buttons.map(ThemedButton::text).toList()
        items?.filter { !presentButtons.contains(it) }
            ?.forEach { item ->
                val btn = ThemedButton(view.context)
                btn.text = item
                btn.selectedTextColor = view.context.color(R.color.textColorDark)
                btn.bgColor = view.context.color(R.color.cardColor)
                btn.selectedBgColor = view.context.attrColor(R.attr.colorPrimary)
                view.addView(btn,
                    RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                )
                btn.applyToCards {
                    it.setViewMargin(vertical = 6.dp)
                    it.setViewPadding(horizontal = 8.dp.toFloat(), vertical = 0.dp.toFloat())
                }
                if (selectedItems?.contains(item) == true)
                    view.selectButton(btn)
            }
        //deselect buttons
        view.buttons.filter { it.isSelected }
            .filter { selectedItems?.contains(it.text) == false }
            .forEach { view.selectButton(it) }
    }
}