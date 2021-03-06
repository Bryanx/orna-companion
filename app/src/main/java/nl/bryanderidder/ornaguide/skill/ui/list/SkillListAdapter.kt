package nl.bryanderidder.ornaguide.skill.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSkillBinding
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.ui.detail.SkillDetailActivity

class SkillListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    StableRecyclerViewAdapter<SkillListAdapter.SkillViewHolder>() {

    private val items: MutableList<Skill> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = parent.binding<ItemSkillBinding>(R.layout.item_skill)
        return SkillViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setSkillId(items[position].id)
                    SkillDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.binding.apply {
            skill = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Skill>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class SkillViewHolder(val binding: ItemSkillBinding) :
        RecyclerView.ViewHolder(binding.root)
}