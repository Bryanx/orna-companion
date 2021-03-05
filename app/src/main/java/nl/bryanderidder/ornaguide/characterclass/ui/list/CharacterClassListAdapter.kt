package nl.bryanderidder.ornaguide.characterclass.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.ui.detail.CharacterClassDetailActivity
import nl.bryanderidder.ornaguide.databinding.ItemCharacterClassBinding
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import timber.log.Timber

class CharacterClassListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    StableRecyclerViewAdapter<CharacterClassListAdapter.CharacterClassViewHolder>() {

    private val items: MutableList<CharacterClass> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterClassViewHolder {
        val binding = parent.binding<ItemCharacterClassBinding>(R.layout.item_character_class)
        return CharacterClassViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setCharacterClassId(items[position].id)
                    CharacterClassDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterClassViewHolder, position: Int) {
        holder.binding.apply {
            characterClass = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<CharacterClass>) {
        Timber.d("setItemList:${classes}")
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class CharacterClassViewHolder(val binding: ItemCharacterClassBinding) :
        RecyclerView.ViewHolder(binding.root)
}