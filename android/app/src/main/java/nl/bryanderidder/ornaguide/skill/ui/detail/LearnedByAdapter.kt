package nl.bryanderidder.ornaguide.skill.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.characterclass.ui.detail.CharacterClassDetailActivity
import nl.bryanderidder.ornaguide.databinding.ItemSkillLearnedByBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill

class LearnedByAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<LearnedByAdapter.LearnedByViewHolder>() {

    private val items: MutableList<Skill.LearnedBy> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearnedByViewHolder {
        val binding =
            parent.binding<ItemSkillLearnedByBinding>(R.layout.item_skill_learned_by)
        return LearnedByViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming)
                    onClickItem(items[position], binding)
            }
        }
    }

    private fun onClickItem(
        learnedBy: Skill.LearnedBy,
        binding: ItemSkillLearnedByBinding
    ) {
        if (learnedBy.specialization) {
//            sharedPrefsUtil.setSpecializationId(learnedBy.id)
//            SpecializationDetailActivity.startActivity(binding.transformationLayout)
        } else {
            sharedPrefsUtil.setCharacterClassId(learnedBy.id)
            CharacterClassDetailActivity.startActivity(binding.transformationLayout)
        }
    }

    override fun onBindViewHolder(holder: LearnedByViewHolder, position: Int) {
        holder.binding.apply {
            learnedBy = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Skill.LearnedBy>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class LearnedByViewHolder(val binding: ItemSkillLearnedByBinding) :
        RecyclerView.ViewHolder(binding.root)
}