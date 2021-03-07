package nl.bryanderidder.ornaguide.item.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemItemBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDetailActivity
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    StableRecyclerViewAdapter<ItemListAdapter.ItemViewHolder>() {

    private val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = parent.binding<ItemItemBinding>(R.layout.item_item)
        return ItemViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setItemId(items[position].id)
                    ItemDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            item = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Item>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}