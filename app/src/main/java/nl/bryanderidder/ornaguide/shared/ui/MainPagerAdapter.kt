package nl.bryanderidder.ornaguide.shared.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassListFragment
import nl.bryanderidder.ornaguide.item.ui.ItemListFragment
import nl.bryanderidder.ornaguide.pet.ui.PetListFragment
import nl.bryanderidder.ornaguide.skill.ui.SkillListFragment
import nl.bryanderidder.ornaguide.specialization.ui.SpecializationListFragment

class MainPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharacterClassListFragment()
            1 -> SpecializationListFragment()
            2 -> SkillListFragment()
            3 -> PetListFragment()
            4 -> ItemListFragment()
            else -> CharacterClassListFragment()
        }
    }
    override fun getItemCount() = 5
}
