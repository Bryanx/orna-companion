package nl.bryanderidder.ornaguide.monster.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemMonsterDropBinding
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDetailActivity
import nl.bryanderidder.ornaguide.monster.model.Monster
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class MonsterDropsAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<MonsterDropsAdapter.MonsterViewHolder>() {

    private val items: MutableList<Monster.IdNamePair> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        val binding =
            parent.binding<ItemMonsterDropBinding>(R.layout.item_monster_drop)
        return MonsterViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setItemId(items[position].id)
                    ItemDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        holder.binding.apply {
            drop = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(skills: List<Monster.IdNamePair>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(skills)
        notifyItemRangeChanged(previousItemSize, skills.size)
    }

    override fun getItemCount() = items.size

    class MonsterViewHolder(val binding: ItemMonsterDropBinding) :
        RecyclerView.ViewHolder(binding.root)
}