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
import nl.bryanderidder.ornaguide.specialization.ui.detail.BoostsAdapter
import nl.bryanderidder.ornaguide.specialization.ui.detail.SpecializationDetailActivity

class SpecializationListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Specialization, SpecializationListAdapter.SpecializationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializationViewHolder =
        SpecializationViewHolder.from(parent)

    override fun onBindViewHolder(holder: SpecializationViewHolder, position: Int) =
        holder.bind(getItem(position), sharedPrefsUtil)

    class SpecializationViewHolder(val binding: ItemSpecializationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newSpecialization: Specialization, sharedPrefsUtil: SharedPrefsUtil) = with (binding) {
            specialization = newSpecialization
            boostsAdapter = BoostsAdapter()
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setSpecializationId(newSpecialization.id)
                    SpecializationDetailActivity.startActivity(transformationLayout)
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup): SpecializationViewHolder =
                SpecializationViewHolder(parent.binding(R.layout.item_specialization))
        }
    }

    override fun submitList(list: MutableList<Specialization>?) {
        super.submitList(list?.filter { it.isFiltered })
    }

    class DiffCallback : DiffUtil.ItemCallback<Specialization>() {
        override fun areItemsTheSame(oldItem: Specialization, newItem: Specialization) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Specialization, newItem: Specialization) =
            oldItem == newItem
    }
}