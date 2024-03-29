package nl.bryanderidder.ornaguide.characterclass.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.databinding.ItemCharacterClassPassiveBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.ui.detail.SkillDetailActivity

class PassivesAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<PassivesAdapter.CharacterClassViewHolder>() {

    private val items: MutableList<CharacterClass.Passive> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterClassViewHolder {
        val binding =
            parent.binding<ItemCharacterClassPassiveBinding>(R.layout.item_character_class_passive)
        return CharacterClassViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setSkillId(items[position].id)
                    SkillDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterClassViewHolder, position: Int) {
        holder.binding.apply {
            passive = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<CharacterClass.Passive>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class CharacterClassViewHolder(val binding: ItemCharacterClassPassiveBinding) :
        RecyclerView.ViewHolder(binding.root)
}