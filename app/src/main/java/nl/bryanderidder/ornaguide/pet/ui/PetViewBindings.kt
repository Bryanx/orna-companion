package nl.bryanderidder.ornaguide.pet.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.pet.model.Pet

object PetViewBindings {
    @JvmStatic
    @BindingAdapter("adapterPetList")
    fun bindAdapterPetList(view: RecyclerView, items: List<Pet>?) {
        (view.adapter as PetListAdapter).setItemList(items ?: listOf())
    }
}