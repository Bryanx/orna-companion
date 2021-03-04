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
    @BindingAdapter("adapterPassiveList")
    fun bindAdapterPassiveList(view: RecyclerView, items: List<CharacterClass.Passive>?) {
        if (view.adapter == null)
            view.adapter = PassivesAdapter()
        (view.adapter as PassivesAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("adapterLearnList")
    fun bindAdapterLearnList(view: RecyclerView, items: List<CharacterClass.Learn>?) {
        if (view.adapter == null)
            view.adapter = LearnsAdapter()
        (view.adapter as LearnsAdapter).setItemList(items ?: listOf())
    }
}