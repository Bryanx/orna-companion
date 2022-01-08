package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

object ColorViewBindings {

    @JvmStatic
    @BindingAdapter("bgColor1", "bgColor1Condition")
    fun bindBgColor1(card: MaterialCardView, color: Int, condition: Boolean) {
        if (condition)
            card.setCardBackgroundColor(color)
    }

    @JvmStatic
    @BindingAdapter("bgColor2", "bgColor2Condition")
    fun bindBgColor2(card: MaterialCardView, color: Int, condition: Boolean) {
        if (condition)
            card.setCardBackgroundColor(color)
    }

    @JvmStatic
    @BindingAdapter("textColor1", "textColor1Condition")
    fun bindTextColor1(tv: TextView, color: Int, condition: Boolean) {
        if (condition)
            tv.setTextColor(color)
    }

    @JvmStatic
    @BindingAdapter("textColor2", "textColor2Condition")
    fun bindTextColor2(tv: TextView, color: Int, condition: Boolean) {
        if (condition)
            tv.setTextColor(color)
    }
}