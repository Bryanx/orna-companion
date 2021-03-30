package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuDiscoverBinding
import nl.bryanderidder.ornaguide.shared.ui.menu.discover.ItemCategory
import nl.bryanderidder.ornaguide.shared.ui.menu.discover.ItemCategoryListAdapter
import nl.bryanderidder.ornaguide.shared.util.color


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
        requireActivity().window.statusBarColor =
            requireContext().color(R.color.backgroundColorDark)
        return binding {
            lifecycleOwner = this@DiscoverFragment
            recyclerView.adapter = ItemCategoryListAdapter()
            (recyclerView.adapter as ItemCategoryListAdapter).submitList(ItemCategory.CATEGORIES)
        }.root
    }
}