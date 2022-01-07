package nl.bryanderidder.ornaguide.shared.ui.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.shared.util.dp
import kotlin.math.ceil
import kotlin.math.round

/**
 * GridLayoutManager that stretches items to use full width and height
 * @author Bryan de Ridder
 */
class StretchingGridLayoutManager : GridLayoutManager {
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context?, spanCount: Int) : super(context, spanCount)
    constructor(
        context: Context?,
        spanCount: Int,
        orientation: Int,
        reverseLayout: Boolean,
    ) : super(context, spanCount, orientation, reverseLayout)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        spanLayoutSize(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(
        c: Context?,
        attrs: AttributeSet?,
    ): RecyclerView.LayoutParams = spanLayoutSize(super.generateLayoutParams(c, attrs))

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): RecyclerView.LayoutParams =
        spanLayoutSize(super.generateLayoutParams(lp))

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean =
        super.checkLayoutParams(lp)

    override fun canScrollVertically(): Boolean = false

    override fun canScrollHorizontally(): Boolean = false

    private fun getHorizontalSpace(): Int = width - paddingRight - paddingLeft

    private fun getVerticalSpace(): Int = height - paddingBottom - paddingTop - 95.dp

    private fun spanLayoutSize(layoutParams: RecyclerView.LayoutParams): RecyclerView.LayoutParams {
        when (orientation) {
            HORIZONTAL -> layoutParams.width = round(getHorizontalSpace() / maxItems()).toInt()
            VERTICAL -> layoutParams.height = round(getVerticalSpace() / maxItems()).toInt()
        }
        return layoutParams
    }

    private fun maxItems() = ceil((itemCount / spanCount).toDouble())
}