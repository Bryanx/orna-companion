package nl.bryanderidder.ornaguide.save.ui.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListFragment

class SaveListFilterPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return CharacterClassListFragment()
        return FILTER_TAB_SAVES.values.toList()[position].invoke()
    }

    override fun getItemCount(): Int = FILTER_TAB_SAVES.size

    companion object {
        val FILTER_TAB_SAVES = mapOf<String, () -> Fragment>(
            "Tier" to ::SaveListFilterTierFragment,
            "Type" to ::SaveListFilterTypeFragment,
        )

        val FILTER_TAB_LABELS: List<String> = FILTER_TAB_SAVES.keys.toList()
    }
}
