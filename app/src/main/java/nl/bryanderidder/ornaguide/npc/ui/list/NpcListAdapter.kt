package nl.bryanderidder.ornaguide.npc.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemNpcBinding
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.ui.detail.NpcDetailActivity
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class NpcListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    StableRecyclerViewAdapter<NpcListAdapter.NpcViewHolder>() {

    private val items: MutableList<Npc> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NpcViewHolder {
        val binding = parent.binding<ItemNpcBinding>(R.layout.item_npc)
        return NpcViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setNpcId(items[position].id)
                    NpcDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: NpcViewHolder, position: Int) {
        holder.binding.apply {
            npc = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Npc>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class NpcViewHolder(val binding: ItemNpcBinding) :
        RecyclerView.ViewHolder(binding.root)
}