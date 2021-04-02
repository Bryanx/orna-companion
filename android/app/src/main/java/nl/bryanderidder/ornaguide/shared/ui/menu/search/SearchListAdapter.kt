package nl.bryanderidder.ornaguide.shared.ui.menu.search

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
import nl.bryanderidder.ornaguide.databinding.ItemSearchResultBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDetailActivity
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDetailActivity
import nl.bryanderidder.ornaguide.npc.model.Npc
import nl.bryanderidder.ornaguide.npc.ui.detail.NpcDetailActivity
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.ui.detail.PetDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.ui.detail.SkillDetailActivity
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.ui.detail.SpecializationDetailActivity

class SearchListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<SearchResult, SearchListAdapter.SearchResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = parent.binding<ItemSearchResultBinding>(R.layout.item_search_result)
        return SearchResultViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    onClickSearchItem(getItem(position), binding)
                }
            }
        }
    }

    private fun onClickSearchItem(
        searchResult: SearchResult,
        binding: ItemSearchResultBinding
    ) {
        when (searchResult.type) {
            CharacterClass.NAME -> {
                sharedPrefsUtil.setCharacterClassId(searchResult.id)
                CharacterClassDetailActivity.startActivity(binding.transformationLayout)
            }
            Skill.NAME -> {
                sharedPrefsUtil.setSkillId(searchResult.id)
                SkillDetailActivity.startActivity(binding.transformationLayout)
            }
            Specialization.NAME -> {
                sharedPrefsUtil.setSpecializationId(searchResult.id)
                SpecializationDetailActivity.startActivity(binding.transformationLayout)
            }
            Pet.NAME -> {
                sharedPrefsUtil.setPetId(searchResult.id)
                PetDetailActivity.startActivity(binding.transformationLayout)
            }
            Item.NAME -> {
                sharedPrefsUtil.setItemId(searchResult.id)
                ItemDetailActivity.startActivity(binding.transformationLayout)
            }
            Monster.NAME -> {
                sharedPrefsUtil.setMonsterId(searchResult.id)
                MonsterDetailActivity.startActivity(binding.transformationLayout)
            }
            Npc.NAME -> {
                sharedPrefsUtil.setNpcId(searchResult.id)
                NpcDetailActivity.startActivity(binding.transformationLayout)
            }
            Achievement.NAME -> {
                sharedPrefsUtil.setAchievementId(searchResult.id)
                AchievementDetailActivity.startActivity(binding.transformationLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.binding.apply {
            searchResult = getItem(position)
            executePendingBindings()
        }
    }

    class SearchResultViewHolder(val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult) =
            oldItem == newItem
    }
}