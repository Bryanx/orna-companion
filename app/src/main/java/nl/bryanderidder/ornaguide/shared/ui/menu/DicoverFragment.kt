package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuDiscoverBinding
import nl.bryanderidder.ornaguide.shared.ui.MainPagerAdapter
import nl.bryanderidder.ornaguide.shared.util.onPageSelected


/**
 * Main discover fragment
 * @author Bryan de Ridder
 */
class DicoverFragment :
    BindingFragment<FragmentMenuDiscoverBinding>(R.layout.fragment_menu_discover) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            mainViewpager.adapter =
                MainPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
            mainViewpager.offscreenPageLimit = 3
            mainViewpager.onPageSelected {
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(binding.tabLayout, binding.mainViewpager) { tab, position ->
            tab.text = MainPagerAdapter.DISCOVER_MENU_LABELS[(position)]
        }.attach()
    }
}