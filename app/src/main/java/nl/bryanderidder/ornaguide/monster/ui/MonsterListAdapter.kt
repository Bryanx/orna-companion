package nl.bryanderidder.ornaguide.monster.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemMonsterBinding
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter
import timber.log.Timber

class MonsterListAdapter(private val sessionVM: SessionViewModel) :
    StableRecyclerViewAdapter<MonsterListAdapter.MonsterViewHolder>() {

    private val items: MutableList<Monster> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        val binding = parent.binding<ItemMonsterBinding>(R.layout.item_monster)
        return MonsterViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                val currentClickedAt = SystemClock.elapsedRealtime()
//                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
//                    sessionVM.monster.value = items[position]
//                    MonsterActivity.startActivity(binding.transformationLayout)
//                    onClickedAt = currentClickedAt
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        holder.binding.apply {
            monster = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Monster>) {
        Timber.d("setItemList:${classes}")
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class MonsterViewHolder(val binding: ItemMonsterBinding) :
        RecyclerView.ViewHolder(binding.root)
}