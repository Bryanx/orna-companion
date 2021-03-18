package nl.bryanderidder.ornaguide.skill.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSkillBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.ui.detail.SkillDetailActivity

class SkillListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Skill, SkillListAdapter.SkillViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = parent.binding<ItemSkillBinding>(R.layout.item_skill)
        return SkillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.binding.apply {
            skill = getItem(position)
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setSkillId(getItem(position).id)
                    SkillDetailActivity.startActivity(transformationLayout)
                }
            }
        }
    }

    class SkillViewHolder(val binding: ItemSkillBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Skill>() {
        override fun areItemsTheSame(oldItem: Skill, newItem: Skill) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Skill, newItem: Skill) =
            oldItem == newItem
    }
}