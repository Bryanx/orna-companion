package nl.bryanderidder.ornaguide.npc.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.ui.detail.NpcQuestsAdapter
import nl.bryanderidder.ornaguide.npc.ui.list.NpcListAdapter

object NpcViewBinding {

    @JvmStatic
    @BindingAdapter("adapterNpcList")
    fun bindAdapterNpcList(view: RecyclerView, items: List<Npc>?) {
        (view.adapter as NpcListAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("npcQuestsAdapter", "npcQuestsList", requireAll = true)
    fun bindNpcQuestsAdapter(view: RecyclerView, adapter: NpcQuestsAdapter, items: List<Npc.Quest>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }
}