package nl.bryanderidder.ornaguide.specialization.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogSpecializationFilterBoostBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class SpecializationListFilterBoostFragment : BindingFragment<FragmentDialogSpecializationFilterBoostBinding>(R.layout.fragment_dialog_specialization_filter_boost) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SpecializationListFilterBoostFragment
            vm = getSharedViewModel()
        }.root
    }
}