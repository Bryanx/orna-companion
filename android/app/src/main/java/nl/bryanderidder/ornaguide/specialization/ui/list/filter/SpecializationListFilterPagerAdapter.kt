package nl.bryanderidder.ornaguide.specialization.ui.list.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListFragment

class SpecializationListFilterPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return CharacterClassListFragment()
        return FILTER_TAB_SPECIALIZATIONS.values.toList()[position].invoke()
    }

    override fun getItemCount(): Int = FILTER_TAB_SPECIALIZATIONS.size

    companion object {
        val FILTER_TAB_SPECIALIZATIONS = mapOf<String, () -> Fragment>(
            "Tier" to ::SpecializationListFilterTierFragment,
            "Boost" to ::SpecializationListFilterBoostFragment,
            "Preferred Weapon" to ::SpecializationListFilterPreferredWeaponFragment,
        )

        val FILTER_TAB_LABELS: List<String> = FILTER_TAB_SPECIALIZATIONS.keys.toList()
    }
}
