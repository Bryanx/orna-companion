package nl.bryanderidder.ornaguide.characterclass.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.databinding.ItemCharacterClassLearnBinding
import nl.bryanderidder.ornaguide.databinding.ItemCharacterClassPassiveBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import timber.log.Timber

class LearnsAdapter : RecyclerView.Adapter<LearnsAdapter.CharacterClassViewHolder>() {

    private val items: MutableList<CharacterClass.Learn> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterClassViewHolder {
        val binding =
            parent.binding<ItemCharacterClassLearnBinding>(R.layout.item_character_class_learn)
        return CharacterClassViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                val currentClickedAt = SystemClock.elapsedRealtime()
//                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
//                    sessionVM.characterClass.value = items[position]
//                    CharacterClassActivity.startActivity(binding.transformationLayout)
//                    onClickedAt = currentClickedAt
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterClassViewHolder, position: Int) {
        holder.binding.apply {
            learn = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<CharacterClass.Learn>) {
        Timber.d("setItemList:${classes}")
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class CharacterClassViewHolder(val binding: ItemCharacterClassLearnBinding) :
        RecyclerView.ViewHolder(binding.root)
}