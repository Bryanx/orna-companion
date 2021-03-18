package nl.bryanderidder.ornaguide.pet.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemPetBinding
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.ui.detail.PetDetailActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil

class PetListAdapter(private val sharedPrefsUtil: SharedPrefsUtil) :
    ListAdapter<Pet, PetListAdapter.PetViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = parent.binding<ItemPetBinding>(R.layout.item_pet)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.binding.apply {
            pet = getItem(position)
            executePendingBindings()
            cardView.setOnClickListener {
                if (!transformationLayout.isTransforming) {
                    sharedPrefsUtil.setPetId(getItem(position).id)
                    PetDetailActivity.startActivity(transformationLayout)
                }
            }
        }
    }

    class PetViewHolder(val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Pet>() {
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pet, newItem: Pet) =
            oldItem == newItem
    }
}