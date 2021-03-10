package nl.bryanderidder.ornaguide.item.ui.list.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListFragment


class ItemListFilterPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return CharacterClassListFragment()
        return FILTER_TAB_ITEMS.values.toList()[position].invoke()
    }

    override fun getItemCount() = FILTER_TAB_ITEMS.size

    companion object {
        val FILTER_TAB_ITEMS = mapOf<String, () -> Fragment>(
            "Tier" to { ItemListFilterTierFragment() },
            "Type" to { ItemListFilterTypeFragment() },
            "Element" to { ItemListFilterElementFragment() },
            "Equip class" to { ItemListFilterEquippedByFragment() },
        )

        val FILTER_TAB_LABELS: List<String> = FILTER_TAB_ITEMS.keys.toList()
    }
}
