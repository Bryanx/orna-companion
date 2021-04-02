package nl.bryanderidder.ornaguide.shared.ui.element

import android.content.Context
import android.util.AttributeSet
import nl.bryanderidder.ornaguide.monster.ui.detail.ElementsAdapter
import nl.bryanderidder.ornaguide.shared.ui.FlexRecyclerView


/**
 * Shows a list of elements
 * @author Bryan de Ridder
 */
class ElementsRecyclerView(context: Context, attrs: AttributeSet) : FlexRecyclerView(context, attrs) {
    init {
        adapter = ElementsAdapter()
        isClickable = false
    }
}