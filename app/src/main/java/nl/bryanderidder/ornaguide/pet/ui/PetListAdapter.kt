package nl.bryanderidder.ornaguide.pet.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemPetBinding
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import timber.log.Timber

class PetListAdapter(private val sessionVM: SessionViewModel) :
    RecyclerView.Adapter<PetListAdapter.PetViewHolder>() {

    private val items: MutableList<Pet> = mutableListOf()
    private var onClickedAt = 0L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = parent.binding<ItemPetBinding>(R.layout.item_pet)
        return PetViewHolder(binding).apply {
            binding.root.setOnClickListener {
//                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
//                    ?: return@setOnClickListener
//                val currentClickedAt = SystemClock.elapsedRealtime()
//                if (currentClickedAt - onClickedAt > binding.transformationLayout.duration) {
//                    sessionVM.pet.value = items[position]
//                    PetActivity.startActivity(binding.transformationLayout)
//                    onClickedAt = currentClickedAt
//                }
            }
        }
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        holder.binding.apply {
            pet = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<Pet>) {
        Timber.d("setItemList:${classes}")
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class PetViewHolder(val binding: ItemPetBinding) :
        RecyclerView.ViewHolder(binding.root)
}