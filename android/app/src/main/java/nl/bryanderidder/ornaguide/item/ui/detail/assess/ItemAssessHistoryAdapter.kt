package nl.bryanderidder.ornaguide.item.ui.detail.assess

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemItemAssessBinding
import nl.bryanderidder.ornaguide.item.model.ItemAssess
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.shared.util.navigateSafely

class ItemAssessHistoryAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil,
    private val fragment: ItemAssessHistoryFragment
) :
    ListAdapter<ItemAssess, ItemAssessHistoryAdapter.ItemAssessViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAssessViewHolder =
        ItemAssessViewHolder.from(parent)

    override fun onBindViewHolder(holder: ItemAssessViewHolder, position: Int) =
        holder.bind(getItem(position), sharedPrefsUtil, fragment)

    class ItemAssessViewHolder(val binding: ItemItemAssessBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newItem: ItemAssess, sharedPrefsUtil: SharedPrefsUtil, fragment: ItemAssessHistoryFragment) = with(binding) {
            itemAssess = newItem
            executePendingBindings()
            cardView.setOnClickListener {
                sharedPrefsUtil.setItemAssessId(newItem.id)
                ItemAssessDialogFragment().show(fragment.childFragmentManager, "itemAssessTag")
            }
        }

        companion object {
            fun from(parent: ViewGroup): ItemAssessViewHolder =
                ItemAssessViewHolder(parent.binding(R.layout.item_item_assess))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ItemAssess>() {
        override fun areItemsTheSame(oldItem: ItemAssess, newItem: ItemAssess) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemAssess, newItem: ItemAssess) =
            oldItem == newItem
    }
}