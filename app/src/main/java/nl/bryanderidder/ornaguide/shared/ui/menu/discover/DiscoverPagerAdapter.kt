package nl.bryanderidder.ornaguide.shared.ui.menu.discover

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.achievement.ui.list.AchievementListFragment
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListFragment
import nl.bryanderidder.ornaguide.item.ui.list.ItemListFragment
import nl.bryanderidder.ornaguide.monster.ui.list.MonsterListFragment
import nl.bryanderidder.ornaguide.npc.ui.list.NpcListFragment
import nl.bryanderidder.ornaguide.pet.ui.list.PetListFragment
import nl.bryanderidder.ornaguide.skill.ui.list.SkillListFragment
import nl.bryanderidder.ornaguide.specialization.ui.list.SpecializationListFragment

class DiscoverPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount)
            return CharacterClassListFragment()
        return DISCOVER_MENU_ITEMS.values.toList()[position].invoke()
    }

    override fun getItemCount() = DISCOVER_MENU_ITEMS.size

    companion object {
        val DISCOVER_MENU_ITEMS = mapOf<String, () -> Fragment>(
            "Items" to ::ItemListFragment,
            "Monsters" to ::MonsterListFragment,
            "Skills" to ::SkillListFragment,
            "Classes" to ::CharacterClassListFragment,
            "Pets" to ::PetListFragment,
            "Specializations" to ::SpecializationListFragment,
            "NPCs" to ::NpcListFragment,
            "Achievements" to ::AchievementListFragment,
        )

        val DISCOVER_MENU_LABELS: List<String> = DISCOVER_MENU_ITEMS.keys.toList()
    }
}
