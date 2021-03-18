package nl.bryanderidder.ornaguide.specialization.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.ui.detail.BoostsAdapter
import nl.bryanderidder.ornaguide.specialization.ui.list.SpecializationListAdapter

object SpecializationViewBinding {

    @JvmStatic
    @BindingAdapter("adapterSpecialization", "adapterSpecializationList")
    fun bindAdapterSpecializationList(view: RecyclerView, adapter: SpecializationListAdapter, items: List<Specialization>?) {
        if (view.adapter == null)
            view.adapter = adapter
        (view.adapter as SpecializationListAdapter).submitList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("boostsAdapter", "adapterBoostsList", requireAll = true)
    fun bindAdapterBoostList(view: RecyclerView, adapter: BoostsAdapter, items: List<Specialization.Boost>?) {
        if (view.adapter == null)
            view.adapter = adapter
        (view.adapter as BoostsAdapter).submitList(items ?: listOf())
    }

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