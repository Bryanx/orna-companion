package nl.bryanderidder.ornaguide.specialization.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSpecializationBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.ui.detail.SpecializationDetailActivity

class SpecializationListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Specialization, SpecializationListAdapter.SpecializationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializationViewHolder {
        val binding = parent.binding<ItemSpecializationBinding>(R.layout.item_specialization)
        return SpecializationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecializationViewHolder, position: Int) {
        holder.binding.apply {
            specialization = getItem(position)
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setSpecializationId(getItem(position).id)
                    SpecializationDetailActivity.startActivity(transformationLayout)
                }
            }
        }
    }

    class SpecializationViewHolder(val binding: ItemSpecializationBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Specialization>() {
        override fun areItemsTheSame(oldItem: Specialization, newItem: Specialization) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Specialization, newItem: Specialization) =
            oldItem == newItem
    }
}