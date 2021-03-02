package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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
}