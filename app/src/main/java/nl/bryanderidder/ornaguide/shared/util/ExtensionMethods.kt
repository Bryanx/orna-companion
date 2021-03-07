package nl.bryanderidder.ornaguide.shared.util

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun View.setViewPadding(
    left: Float? = null, top: Float? = null,
    right: Float? = null, bottom: Float? = null,
    horizontal: Float? = null, vertical: Float? = null,
    all: Float? = null
) {
    if (listOfNotNull(left, top, right, bottom, horizontal, vertical, all).any { it < 0f }) return
    all?.let { setPadding(it.toInt(), it.toInt(), it.toInt(), it.toInt()) }
    horizontal?.let { setPadding(it.toInt(), paddingTop, it.toInt(), paddingBottom) }
    vertical?.let { setPadding(paddingLeft, it.toInt(), paddingRight, it.toInt()) }
    setPadding(
        left?.toInt() ?: paddingLeft,
        top?.toInt() ?: paddingTop,
        right?.toInt() ?: paddingRight,
        bottom?.toInt() ?: paddingBottom
    )
}

fun View.setViewMargin(
    left: Int? = null, top: Int? = null,
    right: Int? = null, bottom: Int? = null,
    horizontal: Int? = null, vertical: Int? = null,
    all: Int? = null
) {
    if (listOfNotNull(left, top, right, bottom, horizontal, vertical, all).any { it < 0f }) return
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        all?.let { setMargins(it, it, it, it) }
        horizontal?.let { setMargins(it, topMargin, it, bottomMargin) }
        vertical?.let { setMargins(leftMargin, it, rightMargin, it) }
        setMargins(
            left ?: leftMargin,
            top ?: topMargin,
            right ?: rightMargin,
            bottom ?: bottomMargin
        )
    }
}

fun Context.color(id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.attrColor(@ColorInt resId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data

}

fun Context.withDelay(timeMillis: Long, doBefore: () -> Unit, doAfter: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        doBefore.invoke()
        delay(timeMillis)
        Handler(mainLooper).post {
            doAfter.invoke()
        }
    }
}

fun Context.getActionBarHeight(): Int {
    val tv = TypedValue()
    if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        return TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
    return 90.dp
}

fun <V : View> BottomSheetBehavior<V>.onSlide(onSlide: (Float) -> Unit) {
    addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            onSlide.invoke(slideOffset)
        }
    })
}

fun ViewPager2.onPageSelected(onSelected: (Int) -> Unit) {
    registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            onSelected(position)
        }
    })
}

fun Context.getNavBarHeight(): Int = 56.dp

// Returns an attribute
fun Context.getAttr(id: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(id, typedValue, true)
    return typedValue.data
}

fun ImageView.setTint(id: Int, blendMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) {
    this.setColorFilter(id, blendMode)
}

// Darkens an int color by a certain factor
fun Int.setAlpha(factor: Float): Int = ColorUtils.setAlphaComponent(this, (factor * 255).toInt())

internal val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()