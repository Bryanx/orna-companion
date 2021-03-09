package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuSearchBinding
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchListAdapter
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.focusAndShowKeyboard
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Main search fragment
 * @author Bryan de Ridder
 */
class SearchFragment :
    BindingFragment<FragmentMenuSearchBinding>(R.layout.fragment_menu_search) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().color(R.color.cardColor)
        return binding {
            lifecycleOwner = this@SearchFragment
            adapter = SearchListAdapter(get())
            vm = getViewModel()
            searchInputField.focusAndShowKeyboard()
        }.root
    }
}