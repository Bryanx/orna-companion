package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuGuidesBinding
import nl.bryanderidder.ornaguide.shared.ui.menu.guides.GuideListAdapter
import nl.bryanderidder.ornaguide.shared.util.color
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel


/**
 * Main discover fragment
 * @author Bryan de Ridder
 */
class GuidesFragment :
    BindingFragment<FragmentMenuGuidesBinding>(R.layout.fragment_menu_guides) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().color(R.color.backgroundColorDark)
        return binding{
            lifecycleOwner = this@GuidesFragment
            adapter = GuideListAdapter(get())
            vm = getViewModel()
        }.root
    }
}