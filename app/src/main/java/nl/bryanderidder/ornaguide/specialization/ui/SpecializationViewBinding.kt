package nl.bryanderidder.ornaguide.specialization.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.color

object SpecializationViewBinding {

    @JvmStatic
    @BindingAdapter("boostTextColor")
    fun bindBoostTextColor(view: TextView, value: Int?) = view.setTextColor(
        view.context.color(
            if (value == null || value >= 0)
                R.color.green
            else
                R.color.red
        )
    )
}