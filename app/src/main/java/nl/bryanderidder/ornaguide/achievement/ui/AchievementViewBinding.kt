package nl.bryanderidder.ornaguide.achievement.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.ui.list.AchievementListAdapter

object AchievementViewBinding {

    @JvmStatic
    @BindingAdapter("adapterAchievementList")
    fun bindAdapterAchievementList(view: RecyclerView, items: List<Achievement>?) {
        (view.adapter as AchievementListAdapter).setItemList(items ?: listOf())
    }
}