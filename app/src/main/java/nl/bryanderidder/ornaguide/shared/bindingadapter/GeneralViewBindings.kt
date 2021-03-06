package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayout
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.ColorUtil
import nl.bryanderidder.ornaguide.shared.util.color


object GeneralViewBindings {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        if (!text.isNullOrEmpty())
            Toast.makeText(view.context, text, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("glideSrc")
    fun bindGlideSrc(view: AppCompatImageView, url: String?) {
        if (url.isNullOrEmpty())
            return
        val circularProgressDrawable = CircularProgressDrawable(view.context)
            .apply { strokeWidth = 5f }
            .apply { centerRadius = 5f }
            .also { it.start() }
        val requestOptions = RequestOptions()
            .placeholder(circularProgressDrawable)
            .error(R.drawable.ic_baseline_clear_24)
            .skipMemoryCache(true)
            .fitCenter()
        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("glideSrcNoPlaceholder")
    fun bindGlideSrcNoPlaceholder(view: AppCompatImageView, url: String?) {
        if (url.isNullOrEmpty())
            return
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("elementColor")
    fun bindAdapterItemList(view: AppCompatTextView, element: String) {
        val colorId = ColorUtil.getColorForElement(element)
        view.setTextColor(view.context.color(colorId))
    }

    @JvmStatic
    @BindingAdapter("addTextViewElementForItems")
    fun bindAddTextViewElementForItems(view: FlexboxLayout, items: List<String>?) {
        if (view.childCount > 0) return
        items?.forEach {
            val textView = AppCompatTextView(
                ContextThemeWrapper(
                    view.context,
                    R.style.elementStyle
                )
            )
            textView.text = it
            textView.setTextColor(view.context.color(ColorUtil.getColorForElement(it)))
            view.addView(textView)
        }
    }
}