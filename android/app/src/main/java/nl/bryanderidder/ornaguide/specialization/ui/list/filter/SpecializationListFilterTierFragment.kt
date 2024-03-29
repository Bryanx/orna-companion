package nl.bryanderidder.ornaguide.specialization.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogSpecializationFilterTierBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class SpecializationListFilterTierFragment : BindingFragment<FragmentDialogSpecializationFilterTierBinding>(R.layout.fragment_dialog_specialization_filter_tier) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SpecializationListFilterTierFragment
            vm = getSharedViewModel()
        }.root
    }
}