package nl.bryanderidder.ornaguide.item.ui.list.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class ItemListFilterPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return ItemListFilterTierFragment()
        return FILTER_TAB_ITEMS.values.toList()[position].invoke()
    }

    override fun getItemCount() = FILTER_TAB_ITEMS.size

    companion object {
        val FILTER_TAB_ITEMS = mapOf<String, () -> Fragment>(
            "Tier" to ::ItemListFilterTierFragment,
            "Type" to ::ItemListFilterTypeFragment,
            "Element" to ::ItemListFilterElementFragment,
            "Equip class" to ::ItemListFilterEquippedByFragment,
            "Stats" to ::ItemListFilterStatFragment,
            "Gives" to ::ItemListFilterGiveFragment,
            "Immunities" to ::ItemListFilterImmunityFragment,
            "Cures" to ::ItemListFilterCureFragment,
        )

        val FILTER_TAB_LABELS: List<String> = FILTER_TAB_ITEMS.keys.toList()
    }
}
