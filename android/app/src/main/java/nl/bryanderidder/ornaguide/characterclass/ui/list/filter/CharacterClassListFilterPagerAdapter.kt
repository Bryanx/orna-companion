package nl.bryanderidder.ornaguide.characterclass.ui.list.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListFragment

class CharacterClassListFilterPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return CharacterClassListFragment()
        return FILTER_TAB_CharacterClassS.values.toList()[position].invoke()
    }

    override fun getItemCount(): Int = FILTER_TAB_CharacterClassS.size

    companion object {
        val FILTER_TAB_CharacterClassS = mapOf<String, () -> Fragment>(
            "Tier" to ::CharacterClassListFilterTierFragment,
            "Cost Type" to ::CharacterClassListFilterCostTypeFragment,
            "Preferred Weapon" to ::CharacterClassListFilterPreferredWeaponFragment,
        )

        val FILTER_TAB_LABELS: List<String> = FILTER_TAB_CharacterClassS.keys.toList()
    }
}
