package nl.bryanderidder.ornaguide.save.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.ImageUtil.setImage

object SaveViewBinding {
    @JvmStatic
    @BindingAdapter("isSaved")
    fun bindSaveIcon(view: AppCompatImageView, saved: Boolean) {
        if (saved)
            view.setImage(R.drawable.ic_baseline_bookmark_24)
        else
            view.setImage(R.drawable.ic_baseline_bookmark_border_24)
    }
}