package nl.bryanderidder.ornaguide.npc.ui.list.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListFragment

class NpcListFilterPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return CharacterClassListFragment()
        return FILTER_TAB_NPCS.values.toList()[position].invoke()
    }

    override fun getItemCount(): Int = FILTER_TAB_NPCS.size

    companion object {
        val FILTER_TAB_NPCS = mapOf<String, () -> Fragment>(
            "Tier" to ::NpcListFilterTierFragment,
        )

        val FILTER_TAB_LABELS: List<String> = FILTER_TAB_NPCS.keys.toList()
    }
}