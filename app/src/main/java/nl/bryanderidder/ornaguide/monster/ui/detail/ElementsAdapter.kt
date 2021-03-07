package nl.bryanderidder.ornaguide.monster.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ItemElementBinding

class ElementsAdapter : RecyclerView.Adapter<ElementsAdapter.ElementViewHolder>() {

    private val items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val binding = parent.binding<ItemElementBinding>(R.layout.item_element)
        return ElementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        holder.binding.apply {
            element = items[position]
            executePendingBindings()
        }
    }

    fun setItemList(classes: List<String>) {
        val previousItemSize = items.size
        items.clear()
        items.addAll(classes)
        notifyItemRangeChanged(previousItemSize, classes.size)
    }

    override fun getItemCount() = items.size

    class ElementViewHolder(val binding: ItemElementBinding) :
        RecyclerView.ViewHolder(binding.root)
}