package nl.bryanderidder.ornaguide.shared.ui

import androidx.recyclerview.widget.RecyclerView

abstract class StableRecyclerViewAdapter<T : RecyclerView.ViewHolder?> : RecyclerView.Adapter<T>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position
}
