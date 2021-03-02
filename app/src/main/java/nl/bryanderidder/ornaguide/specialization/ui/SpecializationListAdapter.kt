package nl.bryanderidder.ornaguide.specialization.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSpecializationBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import nl.bryanderidder.ornaguide.specialization.model.Specialization
import timber.log.Timber

class SpecializationListAdapter(private val sessionVM: SessionViewModel) :
    RecyclerView.Adapter<SpecializationListAdapter.SpecializationViewHolder>() {

    private val items: MutableList<Specialization> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecializationViewHolder {
        val binding = parent.binding<ItemSpecializationBinding>(R.layout.item_specialization)
        return SpecializationViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                val currentClickedAt = SystemClock.elapsedRealtime()
//                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
//                    sessionVM.specialization.value = items[position]
//                    SpecializationActivity.startActivity(binding.transformationLayout)
//                    onClickedAt = currentClickedAt
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: SpecializationViewHolder, position: Int) {
        holder.binding.apply {
            specialization = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Specialization>) {
        Timber.d("setItemList:${classes}")
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class SpecializationViewHolder(val binding: ItemSpecializationBinding) :
        RecyclerView.ViewHolder(binding.root)
}