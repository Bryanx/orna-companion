package nl.bryanderidder.ornaguide.skill.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSkillBuffedByBinding
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill

class BuffedByAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<BuffedByAdapter.BuffedByViewHolder>() {

    private val items: MutableList<Skill.IdNamePair> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuffedByViewHolder {
        val binding =
            parent.binding<ItemSkillBuffedByBinding>(R.layout.item_skill_buffed_by)
        return BuffedByViewHolder(binding).apply {
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

    override fun onBindViewHolder(holder: BuffedByViewHolder, position: Int) {
        holder.binding.apply {
            buffedBy = items[position]
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

    class BuffedByViewHolder(val binding: ItemSkillBuffedByBinding) :
        RecyclerView.ViewHolder(binding.root)
}