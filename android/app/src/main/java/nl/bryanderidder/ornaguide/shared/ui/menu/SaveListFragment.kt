package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuSaveListBinding
import nl.bryanderidder.ornaguide.save.ui.SaveListAdapter
import nl.bryanderidder.ornaguide.save.ui.SaveListViewModel
import nl.bryanderidder.ornaguide.shared.util.color
import nl.bryanderidder.ornaguide.shared.util.navigateSafely
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel

/**
 * Main saved list fragment
 * @author Bryan de Ridder
 */
class SaveListFragment : BaseMenuFragment<FragmentMenuSaveListBinding>(R.layout.fragment_menu_save_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().color(R.color.backgroundColorDark)
        return binding{
            lifecycleOwner = this@SaveListFragment
            adapter = SaveListAdapter(get())
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                navigateSafely(R.id.action_saveListFragment_to_saveListFilterDialogFragment)
            }
        }.root
    }

    override fun onStart() {
        super.onStart()
        getSharedViewModel<SaveListViewModel>().loadItemsAndFilters()
    }
}