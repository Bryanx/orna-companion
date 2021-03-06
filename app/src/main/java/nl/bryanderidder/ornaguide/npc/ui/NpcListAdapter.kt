package nl.bryanderidder.ornaguide.npc.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemNpcBinding
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter

class NpcListAdapter(private val sessionVM: SessionViewModel) :
    StableRecyclerViewAdapter<NpcListAdapter.NpcViewHolder>() {

    private val items: MutableList<Npc> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NpcViewHolder {
        val binding = parent.binding<ItemNpcBinding>(R.layout.item_npc)
        return NpcViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                val currentClickedAt = SystemClock.elapsedRealtime()
//                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
//                    sessionVM.npc.value = items[position]
//                    NpcActivity.startActivity(binding.transformationLayout)
//                    onClickedAt = currentClickedAt
//                }
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