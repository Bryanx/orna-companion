package nl.bryanderidder.ornaguide.item.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemItemEquippedByBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemEquippedByAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<ItemEquippedByAdapter.ItemViewHolder>() {

    private val items: MutableList<Item.IdNamePair> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            parent.binding<ItemItemEquippedByBinding>(R.layout.item_item_equipped_by)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            equippedBy = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(equippedBys: List<Item.IdNamePair>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(equippedBys)
        notifyItemRangeChanged(previousItemSize, equippedBys.size)
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(val binding: ItemItemEquippedByBinding) :
        RecyclerView.ViewHolder(binding.root)
}