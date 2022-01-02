package nl.bryanderidder.ornaguide.item.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemItemBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(getItem(position), sharedPrefsUtil)

    class ItemViewHolder(val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newItem: Item, sharedPrefsUtil: SharedPrefsUtil) = with (binding) {
            item = newItem
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setItemId(newItem.id)
                    ItemDetailActivity.startActivity(transformationLayout)
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup): ItemViewHolder =
                ItemViewHolder(parent.binding(R.layout.item_item))
        }
    }

    override fun submitList(list: MutableList<Item>?) {
        super.submitList(list?.filter { it.isFiltered })
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Item, newItem: Item) =
            oldItem == newItem
    }
}