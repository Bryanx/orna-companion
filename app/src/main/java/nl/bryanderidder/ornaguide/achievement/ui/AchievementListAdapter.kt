package nl.bryanderidder.ornaguide.achievement.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.achievement.model.Achievement
import nl.bryanderidder.ornaguide.databinding.ItemAchievementBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel

class AchievementListAdapter(private val sessionVM: SessionViewModel) :
    RecyclerView.Adapter<AchievementListAdapter.AchievementViewHolder>() {

    private val items: MutableList<Achievement> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = parent.binding<ItemAchievementBinding>(R.layout.item_achievement)
        return AchievementViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                val currentClickedAt = SystemClock.elapsedRealtime()
//                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
//                    sessionVM.achievement.value = items[position]
//                    AchievementActivity.startActivity(binding.transformationLayout)
//                    onClickedAt = currentClickedAt
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.binding.apply {
            achievement = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Achievement>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class AchievementViewHolder(val binding: ItemAchievementBinding) :
        RecyclerView.ViewHolder(binding.root)
}