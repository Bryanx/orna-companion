package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.ui.ItemListAdapter
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
        view.setTextColor(view.context.color(when (element) {
            "Dark" -> R.color.elementDarkColor
            "Water" -> R.color.elementWaterColor
            "Holy" -> R.color.elementHolyColor
            "Earthen" -> R.color.elementEarthenColor
            "Lightning" -> R.color.elementLightningColor
            "Fire" -> R.color.elementFireColor
            else -> R.color.textColorHeader
        }))
    }
}