package nl.bryanderidder.ornaguide.characterclass.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.ui.detail.LearnsAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.detail.PassivesAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListAdapter

object CharacterClassViewBindings {

    @JvmStatic
    @BindingAdapter("adapterCharacterClassList")
    fun bindAdapterCharacterClassList(view: RecyclerView, items: List<CharacterClass>?) {
        (view.adapter as CharacterClassListAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("passivesAdapter", "adapterPassiveList", requireAll = true)
    fun bindAdapterPassivesList(view: RecyclerView, adapter: PassivesAdapter, items: List<CharacterClass.Passive>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("learnsAdapter", "adapterLearnList", requireAll = true)
    fun bindAdapterLearnList(view: RecyclerView, adapter: LearnsAdapter, items: List<CharacterClass.Learn>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }
}