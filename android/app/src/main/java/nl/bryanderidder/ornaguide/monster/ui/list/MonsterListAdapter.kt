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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder =
        MonsterViewHolder.from(parent)

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) =
        holder.bind(getItem(position), sharedPrefsUtil)

    class MonsterViewHolder(val binding: ItemMonsterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newMonster: Monster, sharedPrefsUtil: SharedPrefsUtil) = with (binding) {
            monster = newMonster
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setMonsterId(newMonster.id)
                    MonsterDetailActivity.startActivity(transformationLayout)
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup): MonsterViewHolder =
                MonsterViewHolder(parent.binding(R.layout.item_monster))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Monster>() {
        override fun areItemsTheSame(oldItem: Monster, newItem: Monster) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Monster, newItem: Monster) =
            oldItem == newItem
    }
}