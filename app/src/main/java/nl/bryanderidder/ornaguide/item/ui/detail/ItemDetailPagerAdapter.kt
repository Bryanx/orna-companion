package nl.bryanderidder.ornaguide.item.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class ItemDetailPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val items: Map<String, () -> Fragment>,
) : FragmentStateAdapter(fm, lifecycle) {

    val labels: List<String> = items.keys.toList()

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return ItemDetailFragment()
        return items.values.toList()[position].invoke()
    }

    override fun getItemCount() = items.size
}
