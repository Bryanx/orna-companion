package nl.bryanderidder.ornaguide.npc.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemNpcBinding
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.ui.detail.NpcDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class NpcListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Npc, NpcListAdapter.NpcViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NpcViewHolder =
        NpcViewHolder.from(parent)

    override fun onBindViewHolder(holder: NpcViewHolder, position: Int) =
        holder.bind(getItem(position), sharedPrefsUtil)

    class NpcViewHolder(val binding: ItemNpcBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newNpc: Npc, sharedPrefsUtil: SharedPrefsUtil) = with (binding) {
            npc = newNpc
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setNpcId(newNpc.id)
                    NpcDetailActivity.startActivity(transformationLayout)
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup): NpcViewHolder =
                NpcViewHolder(parent.binding(R.layout.item_npc))
        }
    }

    override fun submitList(list: MutableList<Npc>?) {
        super.submitList(list?.filter { it.isFiltered })
    }

    class DiffCallback : DiffUtil.ItemCallback<Npc>() {
        override fun areItemsTheSame(oldItem: Npc, newItem: Npc) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Npc, newItem: Npc) =
            oldItem == newItem
    }
}