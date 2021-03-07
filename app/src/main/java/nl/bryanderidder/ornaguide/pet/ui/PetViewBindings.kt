package nl.bryanderidder.ornaguide.pet.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.ui.detail.PetSkillsAdapter
import nl.bryanderidder.ornaguide.pet.ui.list.PetListAdapter

object PetViewBindings {
    @JvmStatic
    @BindingAdapter("adapterPetList")
    fun bindAdapterPetList(view: RecyclerView, items: List<Pet>?) {
        (view.adapter as PetListAdapter).setItemList(items ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("petSkillsAdapter", "petSkillsList", requireAll = true)
    fun bindPetSkillsAdapter(view: RecyclerView, adapter: PetSkillsAdapter, items: List<Pet.Skill>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }
}