package nl.bryanderidder.ornaguide.shared.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMenuSettingsBinding


/**
 * Main settings fragment
 * @author Bryan de Ridder
 */
class SettingsFragment :
    BindingFragment<FragmentMenuSettingsBinding>(R.layout.fragment_menu_settings) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}