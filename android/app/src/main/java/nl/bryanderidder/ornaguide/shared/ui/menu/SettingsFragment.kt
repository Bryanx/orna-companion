package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuSettingsBinding
import nl.bryanderidder.ornaguide.shared.util.color


/**
 * Main settings fragment
 * @author Bryan de Ridder
 */
class SettingsFragment :
    BaseMenuFragment<FragmentMenuSettingsBinding>(R.layout.fragment_menu_settings) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().color(R.color.backgroundColorDark)
        return binding.root
    }
}