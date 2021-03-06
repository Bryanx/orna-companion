package nl.bryanderidder.ornaguide.specialization.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSpecializationBoostBinding
import nl.bryanderidder.ornaguide.specialization.model.Specialization

class BoostsAdapter : RecyclerView.Adapter<BoostsAdapter.SpecializationViewHolder>() {

    private val items: MutableList<Specialization.Boost> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializationViewHolder {
        val binding =
            parent.binding<ItemSpecializationBoostBinding>(R.layout.item_specialization_boost)
        return SpecializationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecializationViewHolder, position: Int) {
        holder.binding.apply {
            boost = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Specialization.Boost>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class SpecializationViewHolder(val binding: ItemSpecializationBoostBinding) :
        RecyclerView.ViewHolder(binding.root)
}