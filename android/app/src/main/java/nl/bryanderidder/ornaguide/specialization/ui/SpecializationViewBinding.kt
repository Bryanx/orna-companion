package nl.bryanderidder.ornaguide.specialization.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.getPlusOrMinusColor

object SpecializationViewBinding {

    @JvmStatic
    @BindingAdapter("boostTextColor")
    fun bindBoostTextColor(view: TextView, value: Int?) =
        view.setTextColor(view.context.getPlusOrMinusColor(value))
}