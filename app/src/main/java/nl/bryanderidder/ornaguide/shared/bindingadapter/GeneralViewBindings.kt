package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.ElementColorUtil
import nl.bryanderidder.ornaguide.shared.util.color

object GeneralViewBindings {
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
    @BindingAdapter("paletteImage")
    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String?) {
        if (url.isNullOrEmpty())
            return
        val context = view.context
        Glide.with(context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("elementColor")
    fun bindAdapterItemList(view: AppCompatTextView, element: String) {
        val colorId = ElementColorUtil.getColorForElement(element)
        view.setTextColor(view.context.color(colorId))
    }

    @JvmStatic
    @BindingAdapter("addTextViewElementForItems")
    fun bindAddTextViewElementForItems(view: FlexboxLayout, items: List<String>?) {
        if (view.childCount > 0) return
        items?.forEach {
            val textView = AppCompatTextView(ContextThemeWrapper(view.context, R.style.elementStyle))
            textView.text = it
            textView.setTextColor(view.context.color(ElementColorUtil.getColorForElement(it)))
            view.addView(textView)
        }
    }
}