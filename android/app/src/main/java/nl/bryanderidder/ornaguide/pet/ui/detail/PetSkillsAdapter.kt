package nl.bryanderidder.ornaguide.pet.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemPetSkillBinding
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.ui.detail.SkillDetailActivity

class PetSkillsAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<PetSkillsAdapter.PetViewHolder>() {

    private val items: MutableList<Pet.Skill> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding =
            parent.binding<ItemPetSkillBinding>(R.layout.item_pet_skill)
        return PetViewHolder(binding).apply {
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

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.binding.apply {
            skill = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(skills: List<Pet.Skill>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(skills)
        notifyItemRangeChanged(previousItemSize, skills.size)
    }

    override fun getItemCount() = items.size

    class PetViewHolder(val binding: ItemPetSkillBinding) :
        RecyclerView.ViewHolder(binding.root)
}