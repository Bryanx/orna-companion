package nl.bryanderidder.ornaguide.skill.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.skill.model.Skill

object SkillViewBinding {
    @JvmStatic
    @BindingAdapter("adapterSkillList")
    fun bindSkillList(view: RecyclerView, items: List<Skill>?) {
        (view.adapter as SkillListAdapter).setItemList(items ?: listOf())
    }
}