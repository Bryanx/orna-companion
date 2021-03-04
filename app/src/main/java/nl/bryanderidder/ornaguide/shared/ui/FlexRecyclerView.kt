package nl.bryanderidder.ornaguide.shared.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


/**
 * Recyclerview that centers and stacks items.
 * @author Bryan de Ridder
 */
class FlexRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    init {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        setLayoutManager(layoutManager)
    }

}