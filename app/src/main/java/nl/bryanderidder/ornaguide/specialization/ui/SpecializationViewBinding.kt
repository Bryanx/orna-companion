package nl.bryanderidder.ornaguide.specialization.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.specialization.model.Specialization

object SpecializationViewBinding {

    @JvmStatic
    @BindingAdapter("adapterSpecializationList")
    fun bindAdapterSpecializationList(view: RecyclerView, items: List<Specialization>?) {
        (view.adapter as SpecializationListAdapter).setItemList(items ?: listOf())
    }
}