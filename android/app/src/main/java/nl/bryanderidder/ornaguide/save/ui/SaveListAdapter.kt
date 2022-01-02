package nl.bryanderidder.ornaguide.save.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.ui.detail.AchievementDetailActivity
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.ui.detail.CharacterClassDetailActivity
import nl.bryanderidder.ornaguide.databinding.ItemSaveBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDetailActivity
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDetailActivity
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.ui.detail.NpcDetailActivity
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.ui.detail.PetDetailActivity
import nl.bryanderidder.ornaguide.save.model.Save
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.ui.detail.SkillDetailActivity
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.ui.detail.SpecializationDetailActivity

class SaveListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Save, SaveListAdapter.SaveViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        val binding = parent.binding<ItemSaveBinding>(R.layout.item_save)
        return SaveViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    onClickSaveItem(getItem(position), binding)
                }
            }
        }
    }

    private fun onClickSaveItem(
        save: Save,
        binding: ItemSaveBinding
    ) {
        when (save.type) {
            CharacterClass.NAME -> {
                sharedPrefsUtil.setCharacterClassId(save.id)
                CharacterClassDetailActivity.startActivity(binding.transformationLayout)
            }
            Skill.NAME -> {
                sharedPrefsUtil.setSkillId(save.id)
                SkillDetailActivity.startActivity(binding.transformationLayout)
            }
            Specialization.NAME -> {
                sharedPrefsUtil.setSpecializationId(save.id)
                SpecializationDetailActivity.startActivity(binding.transformationLayout)
            }
            Pet.NAME -> {
                sharedPrefsUtil.setPetId(save.id)
                PetDetailActivity.startActivity(binding.transformationLayout)
            }
            Item.NAME -> {
                sharedPrefsUtil.setItemId(save.id)
                ItemDetailActivity.startActivity(binding.transformationLayout)
            }
            Monster.NAME -> {
                sharedPrefsUtil.setMonsterId(save.id)
                MonsterDetailActivity.startActivity(binding.transformationLayout)
            }
            Npc.NAME -> {
                sharedPrefsUtil.setNpcId(save.id)
                NpcDetailActivity.startActivity(binding.transformationLayout)
            }
            Achievement.NAME -> {
                sharedPrefsUtil.setAchievementId(save.id)
                AchievementDetailActivity.startActivity(binding.transformationLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
        holder.binding.apply {
            save = getItem(position)
            executePendingBindings()
        }
    }

    class SaveViewHolder(val binding: ItemSaveBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun submitList(list: MutableList<Save>?) {
        super.submitList(list?.filter { it.isFiltered })
    }

    class DiffCallback : DiffUtil.ItemCallback<Save>() {
        override fun areItemsTheSame(oldItem: Save, newItem: Save) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Save, newItem: Save) =
            oldItem == newItem
    }
}