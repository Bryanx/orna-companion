package nl.bryanderidder.ornaguide.skill.ui.list.filter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListFragment

class SkillListFilterPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return CharacterClassListFragment()
        return FILTER_TAB_SKILLS.values.toList()[position].invoke()
    }

    override fun getItemCount(): Int = FILTER_TAB_SKILLS.size

    companion object {
        val FILTER_TAB_SKILLS = mapOf<String, () -> Fragment>(
            "Tier" to ::SkillListFilterTierFragment,
            "Type" to ::SkillListFilterTypeFragment,
            "Element" to ::SkillListFilterElementFragment,
            "Source" to ::SkillListFilterSourceFragment,
            "Cures" to ::SkillListFilterCureFragment,
            "Gives" to ::SkillListFilterGiveFragment,
            "Causes" to ::SkillListFilterCauseFragment,
        )

        val FILTER_TAB_LABELS: List<String> = FILTER_TAB_SKILLS.keys.toList()
    }
}
