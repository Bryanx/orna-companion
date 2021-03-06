package nl.bryanderidder.ornaguide.skill.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemSkillPetsUseBinding
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.model.Skill

class PetsUseAdapter(
    private val sharedPrefsUtil: SharedPrefsUtil
) : RecyclerView.Adapter<PetsUseAdapter.PetsUseViewHolder>() {

    private val items: MutableList<Skill.IdNamePair> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsUseViewHolder {
        val binding =
            parent.binding<ItemSkillPetsUseBinding>(R.layout.item_skill_pets_use)
        return PetsUseViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
//                    sharedPrefsUtil.setPetId(items[position].id)
//                    PetDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PetsUseViewHolder, position: Int) {
        holder.binding.apply {
            petsUse = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Skill.IdNamePair>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class PetsUseViewHolder(val binding: ItemSkillPetsUseBinding) :
        RecyclerView.ViewHolder(binding.root)
}