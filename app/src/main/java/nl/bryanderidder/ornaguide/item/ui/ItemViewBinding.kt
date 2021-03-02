package nl.bryanderidder.ornaguide.item.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.item.model.Item

object ItemViewBinding {

    @JvmStatic
    @BindingAdapter("adapterItemList")
    fun bindAdapterItemList(view: RecyclerView, items: List<Item>?) {
        (view.adapter as ItemListAdapter).setItemList(items ?: listOf())
    }
}