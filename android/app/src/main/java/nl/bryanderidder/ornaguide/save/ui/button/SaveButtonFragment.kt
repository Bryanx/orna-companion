package nl.bryanderidder.ornaguide.save.ui.button

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentButtonSaveBinding
import org.koin.android.viewmodel.ext.android.getViewModel


/**
 * Button implemented on all detail pages where a user can save the item
 * @author Bryan de Ridder
 */
class SaveButtonFragment : BindingFragment<FragmentButtonSaveBinding>(R.layout.fragment_button_save) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SaveButtonFragment
            vm = getViewModel<SaveButtonViewModel>().setType(tag)
        }.root
    }
}