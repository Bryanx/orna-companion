package nl.bryanderidder.ornaguide.characterclass.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentSpecializationListBinding
import nl.bryanderidder.ornaguide.specialization.ui.SpecializationListAdapter
import org.koin.android.viewmodel.ext.android.getViewModel


class SpecializationListFragment : BindingFragment<FragmentSpecializationListBinding>(R.layout.fragment_specialization_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SpecializationListFragment
            adapter = SpecializationListAdapter(getViewModel())
            vm = getViewModel()
        }.root
    }
}