package nl.bryanderidder.ornaguide.shared.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.ornaguide.characterclass.model.CharacterClass
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassAdapter

object RecyclerViewBinding {

  @JvmStatic
  @BindingAdapter("adapterCharacterClassList")
  fun bindAdapterCharacterClassList(view: RecyclerView, items: List<CharacterClass>?) {
    (view.adapter as CharacterClassAdapter).setItemList(items ?: listOf())
  }

  @JvmStatic
  @BindingAdapter("gone")
  fun bindGone(view: View, shouldBeGone: Boolean) {
    view.visibility = if (shouldBeGone) {
      View.GONE
    } else {
      View.VISIBLE
    }
  }
}