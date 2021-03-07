package nl.bryanderidder.ornaguide.shared.ui

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

class MainPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharacterClassListFragment()
            1 -> SpecializationListFragment()
            2 -> SkillListFragment()
            3 -> PetListFragment()
            4 -> ItemListFragment()
            5 -> MonsterListFragment()
            6 -> NpcListFragment()
            7 -> AchievementListFragment()
            else -> CharacterClassListFragment()
        }
    }
    override fun getItemCount() = 8
}
