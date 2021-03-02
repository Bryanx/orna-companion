package nl.bryanderidder.ornaguide.shared.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassListFragment
import nl.bryanderidder.ornaguide.characterclass.ui.PetListFragment
import nl.bryanderidder.ornaguide.characterclass.ui.SpecializationListFragment
import nl.bryanderidder.ornaguide.skill.ui.SkillListFragment
import timber.log.Timber

class MainPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        Timber.i("Navigate to position: $position")
        return when (position) {
            0 -> CharacterClassListFragment()
            1 -> SpecializationListFragment()
            2 -> SkillListFragment()
            3 -> PetListFragment()
            else -> CharacterClassListFragment()
        }
    }
    override fun getItemCount() = 4
}
