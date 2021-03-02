package nl.bryanderidder.ornaguide.shared.ui

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.R.styleable.*
import nl.bryanderidder.ornaguide.shared.util.*


/**
 * Single menu item
 * @author Bryan de Ridder
 */
class BottomMenuItem(context: Context, attrs: AttributeSet) : MaterialCardView(context,attrs) {

    private val imageView = AppCompatImageView(context)
    val textView = TextView(context)

    private var icon: Drawable
        get() = imageView.background
        set(icon) {
            imageView.setImageDrawable(icon)
            imageView.layoutParams = LayoutParams(27.dp, 27.dp)
        }

    init {
        addView(createImageView(), LayoutParams(27.dp, 27.dp))
        addView(createTextView(), LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER_VERTICAL))

        val attrs = context.obtainStyledAttributes(attrs, BottomMenuItem)
        attrs.getDrawable(BottomMenuItem_menuIconSrc)?.let { this.icon = it; }
        attrs.getString(BottomMenuItem_menuText)?.let { this.textView.text = it }
        attrs.recycle()

        setContentPadding(10.dp, 10.dp, 10.dp, 10.dp)
        setCardBackgroundColor(context.color(R.color.menuColor))
        rippleColor = ColorStateList.valueOf(context.attrColor(R.attr.colorAccent).setAlpha(0.15f))
        elevation = 0f
    }

    private fun createTextView(): TextView {
        textView.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER_VERTICAL)
        textView.textSize = 15f //sp
        textView.setTextColor(context.color(R.color.textColor))
        textView.setViewPadding(horizontal = 70.dp.toFloat())
        return textView
    }

    private fun createImageView(): AppCompatImageView {
        imageView.layoutParams = LayoutParams(27.dp, 27.dp)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        return imageView
    }
}