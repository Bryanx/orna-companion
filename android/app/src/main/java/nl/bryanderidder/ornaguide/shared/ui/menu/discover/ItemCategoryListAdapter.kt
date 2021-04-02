package nl.bryanderidder.ornaguide.shared.ui.menu.discover

import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemCategoryBinding

class ItemCategoryListAdapter :
    ListAdapter<ItemCategory, ItemCategoryListAdapter.ItemCategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        val binding = parent.binding<ItemCategoryBinding>(R.layout.item_category)
        return ItemCategoryViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                binding.cardView.findNavController().navigate(getItem(position).layout)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        holder.binding.apply {
            itemCategory = getItem(position)
            executePendingBindings()
        }
    }

    class ItemCategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<ItemCategory>() {
        override fun areItemsTheSame(oldItem: ItemCategory, newItem: ItemCategory) =
            oldItem.drawable == newItem.drawable

        override fun areContentsTheSame(oldItem: ItemCategory, newItem: ItemCategory) =
            oldItem == newItem
    }
}