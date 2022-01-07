package nl.bryanderidder.ornaguide.shared.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.card.MaterialCardView

/**
 * Cardview that doesn't allow clicking children
 * @author Bryan de Ridder
 */
class InterceptTouchCardView : MaterialCardView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}