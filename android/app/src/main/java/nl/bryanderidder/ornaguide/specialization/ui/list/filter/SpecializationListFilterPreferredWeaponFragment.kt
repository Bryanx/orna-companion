package nl.bryanderidder.ornaguide.specialization.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogSpecializationFilterPreferredWeaponBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class SpecializationListFilterPreferredWeaponFragment : BindingFragment<FragmentDialogSpecializationFilterPreferredWeaponBinding>(R.layout.fragment_dialog_specialization_filter_preferred_weapon) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SpecializationListFilterPreferredWeaponFragment
            vm = getSharedViewModel()
        }.root
    }
}