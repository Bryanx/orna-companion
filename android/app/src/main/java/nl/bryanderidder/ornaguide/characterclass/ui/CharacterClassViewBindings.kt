package nl.bryanderidder.ornaguide.characterclass.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.ui.detail.LearnsAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.detail.PassivesAdapter

object CharacterClassViewBindings {

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