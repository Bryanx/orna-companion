package nl.bryanderidder.ornaguide.characterclass

import android.os.SystemClock
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemCharacterClassBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import timber.log.Timber

class CharacterClassAdapter(private val sessionVM: SessionViewModel) :
    RecyclerView.Adapter<CharacterClassAdapter.CharacterClassViewHolder>() {

    private val items: MutableList<CharacterClass> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterClassViewHolder {
        val binding = parent.binding<ItemCharacterClassBinding>(R.layout.item_character_class)
        return CharacterClassViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                val currentClickedAt = SystemClock.elapsedRealtime()
                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
                    sessionVM.characterClass.value = items[position]
                    CharacterClassActivity.startActivity(binding.transformationLayout)
                    onClickedAt = currentClickedAt
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