package nl.bryanderidder.ornaguide.achievement.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.achievement.ui.detail.AchievementDetailActivity
import nl.bryanderidder.ornaguide.databinding.ItemAchievementBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class AchievementListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Achievement, AchievementListAdapter.AchievementViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder =
        AchievementViewHolder.from(parent)

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) =
        holder.bind(getItem(position), sharedPrefsUtil)

    class AchievementViewHolder(val binding: ItemAchievementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newAchievement: Achievement, sharedPrefsUtil: SharedPrefsUtil) = with (binding) {
            achievement = newAchievement
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setAchievementId(newAchievement.id)
                    AchievementDetailActivity.startActivity(transformationLayout)
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup): AchievementViewHolder =
                AchievementViewHolder(parent.binding(R.layout.item_achievement))
        }
    }

    override fun submitList(list: MutableList<Achievement>?) {
        super.submitList(list?.filter { it.isFiltered })
    }

    class DiffCallback : DiffUtil.ItemCallback<Achievement>() {
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement) =
            oldItem == newItem
    }
}