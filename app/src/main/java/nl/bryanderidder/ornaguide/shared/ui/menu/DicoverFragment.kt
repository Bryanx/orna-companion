package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuDiscoverBinding
import nl.bryanderidder.ornaguide.item.ui.list.filter.ItemListFilterDialogFragment
import nl.bryanderidder.ornaguide.shared.ui.MainPagerAdapter
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.showBottomSheet


/**
 * Main discover fragment
 * @author Bryan de Ridder
 */
class DicoverFragment :
    BindingFragment<FragmentMenuDiscoverBinding>(R.layout.fragment_menu_discover) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().color(R.color.backgroundColorDark)
        return binding {
            mainViewpager.adapter = MainPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
            mainViewpager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            filterFab.setOnClickListener {
                onClickFilterFab(MainPagerAdapter.DISCOVER_MENU_LABELS[(mainViewpager.currentItem)])
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(binding.tabLayout, binding.mainViewpager) { tab, position ->
            tab.text = MainPagerAdapter.DISCOVER_MENU_LABELS[(position)]
        }.attach()
    }

    private fun onClickFilterFab(currentTab: String) {
        when (currentTab) {
            "Items" -> showBottomSheet(ItemListFilterDialogFragment())
            else -> null
        }
    }
}