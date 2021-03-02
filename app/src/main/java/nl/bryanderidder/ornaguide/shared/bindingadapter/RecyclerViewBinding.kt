package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.LearnsAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.PassivesAdapter
import nl.bryanderidder.ornaguide.skill.model.Skill
import nl.bryanderidder.ornaguide.skill.ui.SkillListAdapter
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import nl.bryanderidder.ornaguide.specialization.ui.SpecializationListAdapter

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapterCharacterClassList")
    fun bindAdapterCharacterClassList(view: RecyclerView, items: List<CharacterClass>?) {
        (view.adapter as CharacterClassAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("adapterSpecializationList")
    fun bindAdapterSpecializationList(view: RecyclerView, items: List<Specialization>?) {
        (view.adapter as SpecializationListAdapter).setItemList(items ?: listOf())
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

    @JvmStatic
    @BindingAdapter("adapterSkillList")
    fun bindSkillList(view: RecyclerView, items: List<Skill>?) {
        (view.adapter as SkillListAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}