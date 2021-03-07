package nl.bryanderidder.ornaguide.specialization.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSpecializationBinding
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.ui.detail.BoostsAdapter
import nl.bryanderidder.ornaguide.specialization.ui.detail.SpecializationDetailActivity

class SpecializationListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    StableRecyclerViewAdapter<SpecializationListAdapter.SpecializationViewHolder>() {

    private val items: MutableList<Specialization> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializationViewHolder {
        val binding = parent.binding<ItemSpecializationBinding>(R.layout.item_specialization)
        return SpecializationViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setSpecializationId(items[position].id)
                    SpecializationDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: SpecializationViewHolder, position: Int) {
        holder.binding.apply {
            specialization = items[position]
            boostsAdapter = BoostsAdapter()
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Specialization>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class SpecializationViewHolder(val binding: ItemSpecializationBinding) :
        RecyclerView.ViewHolder(binding.root)
}