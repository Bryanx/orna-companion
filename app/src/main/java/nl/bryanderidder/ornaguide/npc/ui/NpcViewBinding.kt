package nl.bryanderidder.ornaguide.npc.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.npc.model.Npc

object NpcViewBinding {

    @JvmStatic
    @BindingAdapter("adapterNpcList")
    fun bindAdapterNpcList(view: RecyclerView, items: List<Npc>?) {
        (view.adapter as NpcListAdapter).setItemList(items ?: listOf())
    }
}