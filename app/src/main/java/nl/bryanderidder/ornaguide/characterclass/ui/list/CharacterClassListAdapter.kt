package nl.bryanderidder.ornaguide.characterclass.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.ui.detail.CharacterClassDetailActivity
import nl.bryanderidder.ornaguide.databinding.ItemCharacterClassBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class CharacterClassListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<CharacterClass, CharacterClassListAdapter.CharacterClassViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterClassViewHolder {
        val binding = parent.binding<ItemCharacterClassBinding>(R.layout.item_character_class)
        return CharacterClassViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterClassViewHolder, position: Int) {
        holder.binding.apply {
            characterClass = getItem(position)
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setCharacterClassId(getItem(position).id)
                    CharacterClassDetailActivity.startActivity(transformationLayout)
                }
            }
        }
    }

    class CharacterClassViewHolder(val binding: ItemCharacterClassBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<CharacterClass>() {
        override fun areItemsTheSame(oldItem: CharacterClass, newItem: CharacterClass) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterClass, newItem: CharacterClass) =
            oldItem == newItem
    }
}