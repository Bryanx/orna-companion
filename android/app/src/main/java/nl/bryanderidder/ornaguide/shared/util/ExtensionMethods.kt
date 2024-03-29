package nl.bryanderidder.ornaguide.shared.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import nl.bryanderidder.ornaguide.R
import timber.log.Timber


fun View.setViewPadding(
    left: Float? = null, top: Float? = null,
    right: Float? = null, bottom: Float? = null,
    horizontal: Float? = null, vertical: Float? = null,
    all: Float? = null,
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

@Suppress("DEPRECATION")
fun Drawable.setCustomColorFilter(color: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    else
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

var View.layoutGravity
    get() = (layoutParams as FrameLayout.LayoutParams).gravity
    set(value) {
        layoutParams = FrameLayout.LayoutParams(
            layoutParams.width,
            layoutParams.height,
            value
        )
    }

fun View.setViewMargin(
    left: Int? = null, top: Int? = null,
    right: Int? = null, bottom: Int? = null,
    horizontal: Int? = null, vertical: Int? = null,
    all: Int? = null,
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

fun Context.getPlusOrMinusColor(value: Int?): Int {
    return if (value == null || value >= 0)
        ContextCompat.getColor(this, R.color.ornaGreen)
    else
        ContextCompat.getColor(this, R.color.red)
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

fun <V : View> BottomSheetBehavior<V>.onSlide(onSlide: (Float) -> Unit) {
    addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            onSlide.invoke(slideOffset)
        }
    })
}

fun ViewPager2.onPageSelected(onSelected: (Int) -> Unit) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            onSelected(position)
        }
    })
}

fun TextInputEditText.focusAndShowKeyboard() {
    post {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// Returns an attribute
fun Context.getAttr(id: Int): Int {
    val typedValue = TypedValue()
    this.theme.resolveAttribute(id, typedValue, true)
    return typedValue.data
}

fun ImageView.setTint(id: Int, blendMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) {
    this.setColorFilter(id, blendMode)
}

fun Fragment.navigateSafely(resId: Int) {
    try {
        findNavController().navigate(resId)
    } catch (e: IllegalArgumentException) {
        Timber.i("Can't open 2 links at once.")
    }
}

fun View.navigateSafely(resId: Int) {
    try {
        findNavController().navigate(resId)
    } catch (e: IllegalArgumentException) {
        Timber.i("Can't open 2 links at once.")
    }
}

// Darkens an int color by a certain factor
fun Int.setAlpha(factor: Float): Int = ColorUtils.setAlphaComponent(this, (factor * 255).toInt())

internal val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()


/**
 * Makes a substring of a string bold.
 * @param textToBold    Text you want to make bold
 * @return              String with bold substring
 */
fun String.makeBold(textToBold: String): SpannableStringBuilder {
    val builder = SpannableStringBuilder()
    if (textToBold.isNotEmpty() && textToBold.trim { it <= ' ' } != "") {
        //for counting start/end indexes
        val testText = toLowerCase()
        val testTextToBold = textToBold.toLowerCase()
        val startingIndex = testText.indexOf(testTextToBold)
        val endingIndex = startingIndex + testTextToBold.length
        //for counting start/end indexes
        if (startingIndex < 0 || endingIndex < 0) {
            return builder.append(this)
        } else if (startingIndex >= 0 && endingIndex >= 0) {
            builder.append(this)
            builder.setSpan(StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0)
        }
    } else {
        return builder.append(this)
    }
    return builder
}

fun <T> List<T>.forEachApply(action: (T) -> Unit): List<T> {
    forEach(action)
    return this
}

fun <T> Flow<List<T>>.asLiveDataIO(scope: CoroutineScope): LiveData<List<T>> =
    asLiveData(scope.coroutineContext + Dispatchers.IO)

fun BottomSheetDialog.setOnShowBottomSheet(callback: (BottomSheetBehavior<FrameLayout>) -> Unit) {
    setOnShowListener { dia ->
        val dialog = dia as BottomSheetDialog
        val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        bottomSheet?.let {
            callback(BottomSheetBehavior.from(it))
        }
    }
}