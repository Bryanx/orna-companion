package nl.bryanderidder.ornaguide.shared.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.children
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.attrColor
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.setAlpha
import nl.bryanderidder.ornaguide.shared.util.withDelay


/**
 * Description
 * @author Bryan de Ridder
 */
class BottomMenu(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var selectListener: (BottomMenuItem, Int) -> Unit
    val menuItems: MutableList<BottomMenuItem> = mutableListOf()

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        if (child is FrameLayout) {
            val item = child.children.iterator().next()
            if (item is BottomMenuItem)
                addClickListeners(item)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun addClickListeners(item: BottomMenuItem) {
        menuItems += item
        item.setOnClickListener {
            context.withDelay(200L, doBefore = {
                setItemColors(item)
            }, doAfter = {
                selectListener.invoke(item, menuItems.indexOf(item))
            })
        }
    }

    private fun setItemColors(item: BottomMenuItem) {
        item.setCardBackgroundColor(context.attrColor(R.attr.colorAccent).setAlpha(0.15f))
        item.textView.setTextColor(context.attrColor(R.attr.colorAccent))
        menuItems.filter { it != item }.forEach {
            it.setCardBackgroundColor(context.color(R.color.menuColor))
            it.textView.setTextColor(context.color(R.color.textColor))
        }
    }


    /** Listen on selection changes. Alternatively you can add onclick listeners to the buttons. */
    fun setOnItemSelectedListener(listener: (BottomMenuItem, Int) -> Unit) {
        this.selectListener = listener
    }

    fun setSelectedItem(currentItem: Int) {
        val selectedItem = menuItems[currentItem]
        setItemColors(selectedItem)
    }

    fun getName(currentItem: Int): String {
        return menuItems[currentItem].textView.text.toString()
    }
}