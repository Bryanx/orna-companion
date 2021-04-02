package nl.bryanderidder.ornaguide.item.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemItemDetailBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class ItemQuestsAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<ItemQuestsAdapter.ItemViewHolder>() {

    private val items: MutableList<Item.IdNamePair> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            parent.binding<ItemItemDetailBinding>(R.layout.item_item_detail)
        return ItemViewHolder(binding).apply {
//            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                if (!binding.transformationLayout.isTransforming) {
//                    sharedPrefsUtil.setQuestId(items[position].id)
//                    QuestDetailActivity.startActivity(binding.transformationLayout)
//                }
//            }
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

    class ItemViewHolder(val binding: ItemItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root)
}