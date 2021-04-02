package nl.bryanderidder.ornaguide.skill.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSkillMonstersUseBinding
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill

class MonstersUseAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<MonstersUseAdapter.MonstersUseViewHolder>() {

    private val items: MutableList<Skill.IdNamePair> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonstersUseViewHolder {
        val binding =
            parent.binding<ItemSkillMonstersUseBinding>(R.layout.item_skill_monsters_use)
        return MonstersUseViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setMonsterId(items[position].id)
                    MonsterDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MonstersUseViewHolder, position: Int) {
        holder.binding.apply {
            monstersUse = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Skill.IdNamePair>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class MonstersUseViewHolder(val binding: ItemSkillMonstersUseBinding) :
        RecyclerView.ViewHolder(binding.root)
}