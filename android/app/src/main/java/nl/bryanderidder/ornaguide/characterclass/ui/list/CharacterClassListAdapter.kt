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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterClassViewHolder =
        CharacterClassViewHolder.from(parent)

    override fun onBindViewHolder(holder: CharacterClassViewHolder, position: Int) =
        holder.bind(getItem(position), sharedPrefsUtil)

    class CharacterClassViewHolder(val binding: ItemCharacterClassBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newCharacterClass: CharacterClass, sharedPrefsUtil: SharedPrefsUtil) = with (binding) {
            characterClass = newCharacterClass
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setCharacterClassId(newCharacterClass.id)
                    CharacterClassDetailActivity.startActivity(transformationLayout)
                }
            }
        }
        companion object {
            fun from(parent: ViewGroup): CharacterClassViewHolder =
                CharacterClassViewHolder(parent.binding(R.layout.item_character_class))
        }
    }

    override fun submitList(list: MutableList<CharacterClass>?) {
        super.submitList(list?.filter { it.isFiltered })
    }

    class DiffCallback : DiffUtil.ItemCallback<CharacterClass>() {
        override fun areItemsTheSame(oldItem: CharacterClass, newItem: CharacterClass) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterClass, newItem: CharacterClass) =
            oldItem == newItem
    }
}