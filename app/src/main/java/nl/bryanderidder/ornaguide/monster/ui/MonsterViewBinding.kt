package nl.bryanderidder.ornaguide.monster.ui

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.setTint

object MonsterViewBinding {

    @JvmStatic
    @BindingAdapter("adapterMonsterList")
    fun bindAdapterMonsterList(view: RecyclerView, items: List<Monster>?) {
        (view.adapter as MonsterListAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("redTextIf")
    fun bindRedTextIf(view: AppCompatTextView, shouldBeRed: Boolean) {
        if (shouldBeRed)
            view.setTextColor(view.context.color(R.color.red))
    }

    @JvmStatic
    @BindingAdapter("redTintIf")
    fun bindRedTintIf(view: AppCompatImageView, shouldBeRed: Boolean) {
        if (shouldBeRed)
            view.setTint(view.context.color(R.color.red))
    }
}