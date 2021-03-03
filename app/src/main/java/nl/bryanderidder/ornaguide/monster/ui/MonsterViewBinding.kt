package nl.bryanderidder.ornaguide.monster.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.monster.model.Monster

object MonsterViewBinding {

    @JvmStatic
    @BindingAdapter("adapterMonsterList")
    fun bindAdapterMonsterList(view: RecyclerView, items: List<Monster>?) {
        (view.adapter as MonsterListAdapter).setItemList(items ?: listOf())
    }
}