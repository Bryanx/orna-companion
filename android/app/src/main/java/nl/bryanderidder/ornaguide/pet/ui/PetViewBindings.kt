package nl.bryanderidder.ornaguide.pet.ui

import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.text.color
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.pet.model.Pet
import nl.bryanderidder.ornaguide.pet.ui.detail.PetSkillsAdapter
import nl.bryanderidder.ornaguide.shared.util.getPlusOrMinusColor

object PetViewBindings {

    @JvmStatic
    @BindingAdapter("petSkillsAdapter", "petSkillsList", requireAll = true)
    fun bindPetSkillsAdapter(view: RecyclerView, adapter: PetSkillsAdapter, items: List<Pet.Skill>?) {
        view.adapter = adapter
        adapter.setItemList(items ?: listOf())
    }
    
    @JvmStatic
    @BindingAdapter("petStats")
    fun bindPetStats(tv: TextView, pet: Pet?) {
        val builder = SpannableStringBuilder()
        pet?.getActiveStats()?.map { (stat, value) ->
            builder.append("$stat chance ")
            builder.color(tv.context.getPlusOrMinusColor(value)) { append("$value%") }
            builder.append("\n")
        }
        tv.text = builder.trimEnd()
    }
}