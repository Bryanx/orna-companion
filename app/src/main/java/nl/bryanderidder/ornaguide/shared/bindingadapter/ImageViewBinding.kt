package nl.bryanderidder.ornaguide.shared.bindingadapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBinding {
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