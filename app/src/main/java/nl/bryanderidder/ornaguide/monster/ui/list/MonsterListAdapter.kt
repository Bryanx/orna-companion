package nl.bryanderidder.ornaguide.monster.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemMonsterBinding
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class MonsterListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Monster, MonsterListAdapter.MonsterViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        val binding = parent.binding<ItemMonsterBinding>(R.layout.item_monster)
        return MonsterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        holder.binding.apply {
            monster = getItem(position)
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setMonsterId(getItem(position).id)
                    MonsterDetailActivity.startActivity(transformationLayout)
                }
            }
        }
    }

    class MonsterViewHolder(val binding: ItemMonsterBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Monster>() {
        override fun areItemsTheSame(oldItem: Monster, newItem: Monster) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Monster, newItem: Monster) =
            oldItem == newItem
    }
}