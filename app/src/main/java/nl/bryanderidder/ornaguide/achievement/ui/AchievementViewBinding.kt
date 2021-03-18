package nl.bryanderidder.ornaguide.achievement.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.ui.list.AchievementListAdapter

object AchievementViewBinding {

    @JvmStatic
    @BindingAdapter("achievementAdapter", "achievementAdapterList")
    fun bindAdapterAchievementList(view: RecyclerView, adapter: AchievementListAdapter, items: List<Achievement>?) {
        if (view.adapter == null)
            view.adapter = adapter
        (view.adapter as AchievementListAdapter).submitList(items ?: listOf())
    }
}