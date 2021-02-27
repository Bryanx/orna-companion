package nl.bryanderidder.ornaguide.shared.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.characterclass.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.CharacterClassAdapter

object RecyclerViewBinding {

  @JvmStatic
  @BindingAdapter("adapterCharacterClassList")
  fun bindAdapterCharacterClassList(view: RecyclerView, items: List<CharacterClass>?) {
    (view.adapter as CharacterClassAdapter).setItemList(items ?: listOf())
  }
}