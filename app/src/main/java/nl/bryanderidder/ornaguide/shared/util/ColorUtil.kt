package nl.bryanderidder.ornaguide.shared.util

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import nl.bryanderidder.ornaguide.R


object ColorUtil {

    fun getAttrColor(context: Context): Int {
        val typedValue = TypedValue()
        val a: TypedArray =
            context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }
}