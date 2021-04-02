package nl.bryanderidder.ornaguide.npc.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemNpcQuestBinding
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class NpcQuestsAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<NpcQuestsAdapter.NpcViewHolder>() {

    private val items: MutableList<Npc.Quest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NpcViewHolder {
        val binding =
            parent.binding<ItemNpcQuestBinding>(R.layout.item_npc_quest)
        return NpcViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                if (!binding.transformationLayout.isTransforming) {
//                    sharedPrefsUtil.setQuestId(quests[position].id)
//                    QuestDetailActivity.startActivity(binding.transformationLayout)
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: NpcViewHolder, position: Int) {
        holder.binding.apply {
            quest = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(skills: List<Npc.Quest>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(skills)
        notifyItemRangeChanged(previousItemSize, skills.size)
    }

    override fun getItemCount() = items.size

    class NpcViewHolder(val binding: ItemNpcQuestBinding) :
        RecyclerView.ViewHolder(binding.root)
}