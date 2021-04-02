package nl.bryanderidder.ornaguide.specialization.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSpecializationBoostBinding
import nl.bryanderidder.ornaguide.specialization.model.Specialization

class BoostsAdapter : ListAdapter<Specialization.Boost, BoostsAdapter.BoostViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoostViewHolder =
        BoostViewHolder.from(parent)

    override fun onBindViewHolder(holder: BoostViewHolder, position: Int) =
        holder.bind(getItem(position))

    class BoostViewHolder(val binding: ItemSpecializationBoostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newBoost: Specialization.Boost) = with (binding) {
            boost = newBoost
            executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): BoostViewHolder =
                BoostViewHolder(parent.binding(R.layout.item_specialization_boost))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Specialization.Boost>() {
        override fun areItemsTheSame(oldItem: Specialization.Boost, newItem: Specialization.Boost) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Specialization.Boost, newItem: Specialization.Boost) =
            oldItem == newItem
    }
}