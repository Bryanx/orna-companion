package nl.bryanderidder.ornaguide.monster.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDropsAdapter
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterQuestsAdapter
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterSkillsAdapter

object MonsterViewBinding {

    @JvmStatic
    @BindingAdapter("monsterSkillsAdapter", "monsterSkillsList", requireAll = true)
    fun bindAdapterLearnList(view: RecyclerView, adapter: MonsterSkillsAdapter, items: List<Monster.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("monsterDropsAdapter", "monsterDropsList", requireAll = true)
    fun bindAdapterLearnList(view: RecyclerView, adapter: MonsterDropsAdapter, items: List<Monster.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("monsterQuestsAdapter", "monsterQuestsList", requireAll = true)
    fun bindAdapterLearnList(view: RecyclerView, adapter: MonsterQuestsAdapter, items: List<Monster.IdNamePair>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }
}