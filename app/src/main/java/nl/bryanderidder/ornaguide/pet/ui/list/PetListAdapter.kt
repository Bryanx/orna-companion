package nl.bryanderidder.ornaguide.pet.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemPetBinding
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.ui.detail.PetDetailActivity
import nl.bryanderidder.ornaguide.shared.ui.StableRecyclerViewAdapter
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class PetListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    StableRecyclerViewAdapter<PetListAdapter.PetViewHolder>() {

    private val items: MutableList<Pet> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = parent.binding<ItemPetBinding>(R.layout.item_pet)
        return PetViewHolder(binding).apply {
            binding.cardView.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                if (!binding.transformationLayout.isTransforming) {
                    sharedPrefsUtil.setPetId(items[position].id)
                    PetDetailActivity.startActivity(binding.transformationLayout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.binding.apply {
            pet = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(pets: List<Pet>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(pets)
        notifyItemRangeChanged(previousItemSize, pets.size)
    }

    override fun getItemCount() = items.size

    class PetViewHolder(val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root)
}