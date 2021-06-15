package nl.bryanderidder.ornaguide.shared.ui.menu.guides

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemGuideBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class GuideListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<String, GuideListAdapter.GuideResultViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideResultViewHolder {
        val binding = parent.binding<ItemGuideBinding>(R.layout.item_guide)
        return GuideResultViewHolder(binding).apply {
//            binding.cardView.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                if (!binding.transformationLayout.isTransforming) {
//                    onClickGuideItem(getItem(position), binding)
//                }
//            }
        }
    }

    private fun onClickGuideItem(
        guideResult: String,
        binding: ItemGuideBinding
    ) {

    }

    override fun onBindViewHolder(holder: GuideResultViewHolder, position: Int) {
        holder.binding.apply {
            guide = getItem(position)
            executePendingBindings()
        }
    }

    class GuideResultViewHolder(val binding: ItemGuideBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }
}