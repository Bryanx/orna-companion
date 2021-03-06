package nl.bryanderidder.ornaguide.shared.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import nl.bryanderidder.ornaguide.R.styleable.*

/**
 * Recyclerview with a customizable FlexboxLayoutManager
 * @author Bryan de Ridder
 */
class FlexRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    init {
        val layoutManager = FlexboxLayoutManager(context)
        val styledAttrs = context.obtainStyledAttributes(attrs, FlexRecyclerView)
        try {
            layoutManager.flexDirection = styledAttrs.getInt(FlexRecyclerView_flexDirectionRecycler, FlexDirection.ROW)
            layoutManager.justifyContent = styledAttrs.getInt(FlexRecyclerView_justifyContentRecycler, JustifyContent.CENTER)
            layoutManager.flexWrap = styledAttrs.getInt(FlexRecyclerView_flexWrapRecycler, FlexWrap.WRAP)
        } finally {
            styledAttrs.recycle()
        }
        setLayoutManager(layoutManager)
    }

}