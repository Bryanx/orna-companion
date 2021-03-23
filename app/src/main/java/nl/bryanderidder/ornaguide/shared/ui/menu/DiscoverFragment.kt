package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.achievement.ui.list.filter.AchievementListFilterDialogFragment
import nl.bryanderidder.ornaguide.characterclass.ui.list.filter.CharacterClassListFilterDialogFragment
import nl.bryanderidder.ornaguide.databinding.FragmentMenuDiscoverBinding
import nl.bryanderidder.ornaguide.item.ui.list.filter.ItemListFilterDialogFragment
import nl.bryanderidder.ornaguide.monster.ui.list.filter.MonsterListFilterDialogFragment
import nl.bryanderidder.ornaguide.npc.ui.list.filter.NpcListFilterDialogFragment
import nl.bryanderidder.ornaguide.pet.ui.list.filter.PetListFilterDialogFragment
import nl.bryanderidder.ornaguide.shared.ui.menu.discover.DiscoverPagerAdapter
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.showBottomSheet
import nl.bryanderidder.ornaguide.skill.ui.list.filter.SkillListFilterDialogFragment
import nl.bryanderidder.ornaguide.specialization.ui.list.filter.SpecializationListFilterDialogFragment


/**
 * Main discover fragment
 * @author Bryan de Ridder
 */
class DiscoverFragment :
    BindingFragment<FragmentMenuDiscoverBinding>(R.layout.fragment_menu_discover) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().color(R.color.backgroundColorDark)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            mainViewpager.adapter = DiscoverPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
            mainViewpager.offscreenPageLimit = 3
            filterFab.setOnClickListener {
                onClickFilterFab(DiscoverPagerAdapter.DISCOVER_MENU_LABELS[(mainViewpager.currentItem)])
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.mainViewpager) { tab, position ->
            tab.text = DiscoverPagerAdapter.DISCOVER_MENU_LABELS[(position)]
        }.attach()
    }

    private fun onClickFilterFab(currentTab: String) {
        when (currentTab) {
            "Items" -> showBottomSheet(ItemListFilterDialogFragment())
            "Monsters" -> showBottomSheet(MonsterListFilterDialogFragment())
            "Skills" -> showBottomSheet(SkillListFilterDialogFragment())
            "Classes" -> showBottomSheet(CharacterClassListFilterDialogFragment())
            "Pets" -> showBottomSheet(PetListFilterDialogFragment())
            "Specializations" -> showBottomSheet(SpecializationListFilterDialogFragment())
            "NPCs" -> showBottomSheet(NpcListFilterDialogFragment())
            "Achievements" -> showBottomSheet(AchievementListFilterDialogFragment())
            else -> null
        }
    }
}