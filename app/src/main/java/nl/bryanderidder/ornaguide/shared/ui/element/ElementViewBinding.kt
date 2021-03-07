package nl.bryanderidder.ornaguide.shared.ui.element

import androidx.databinding.BindingAdapter
import nl.bryanderidder.ornaguide.monster.ui.detail.ElementsAdapter

object ElementViewBinding {
    @JvmStatic
    @BindingAdapter("elementsList", requireAll = true)
    fun bindAdapterElementsList(view: ElementsRecyclerView, items: List<String>?) {
        (view.adapter as ElementsAdapter).setItemList(items ?: listOf())
    }
}