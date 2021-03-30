package nl.bryanderidder.ornaguide.shared.ui.menu.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDiscoverCategoriesBinding
import nl.bryanderidder.ornaguide.shared.util.color

/**
 * List of database categories
 * @author Bryan de Ridder
 */
class DiscoverCategoriesFragment :
    BindingFragment<FragmentDiscoverCategoriesBinding>(R.layout.fragment_discover_categories) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().window.statusBarColor =
            requireContext().color(R.color.backgroundColorDark)
        return binding {
            lifecycleOwner = this@DiscoverCategoriesFragment
            recyclerView.adapter = ItemCategoryListAdapter()
            (recyclerView.adapter as ItemCategoryListAdapter).submitList(ItemCategory.CATEGORIES)
        }.root
    }
}