package nl.bryanderidder.ornaguide.item.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemItemDroppedByBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemDroppedByAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<ItemDroppedByAdapter.ItemViewHolder>() {

    private val items: MutableList<Item.IdNamePair> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            parent.binding<ItemItemDroppedByBinding>(R.layout.item_item_dropped_by)
        return ItemViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming && items[position].id != 0) {
                    sharedPrefsUtil.setMonsterId(items[position].id)
                    MonsterDetailActivity.startActivity(binding.transformationLayout)
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

    fun setItemList(equippedBys: List<Item.IdNamePair>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(equippedBys)
        notifyItemRangeChanged(previousItemSize, equippedBys.size)
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(val binding: ItemItemDroppedByBinding) :
        RecyclerView.ViewHolder(binding.root)
}