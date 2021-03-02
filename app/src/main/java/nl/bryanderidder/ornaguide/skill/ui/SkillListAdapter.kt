package nl.bryanderidder.ornaguide.skill.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSkillBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import nl.bryanderidder.ornaguide.skill.model.Skill
import timber.log.Timber

class SkillListAdapter(private val sessionVM: SessionViewModel) :
    RecyclerView.Adapter<SkillListAdapter.SkillViewHolder>() {

    private val items: MutableList<Skill> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = parent.binding<ItemSkillBinding>(R.layout.item_skill)
        return SkillViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                val currentClickedAt = SystemClock.elapsedRealtime()
//                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
//                    sessionVM.characterClass.value = items[position]
//                    SkillActivity.startActivity(binding.transformationLayout)
//                    onClickedAt = currentClickedAt
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.binding.apply {
            skill = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Skill>) {
        Timber.d("setItemList:${classes}")
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class SkillViewHolder(val binding: ItemSkillBinding) :
        RecyclerView.ViewHolder(binding.root)
}