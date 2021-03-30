package nl.bryanderidder.ornaguide.specialization.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentSpecializationListBinding
import nl.bryanderidder.ornaguide.shared.util.showBottomSheet
import nl.bryanderidder.ornaguide.specialization.ui.list.filter.SpecializationListFilterDialogFragment
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class SpecializationListFragment : BindingFragment<FragmentSpecializationListBinding>(R.layout.fragment_specialization_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SpecializationListFragment
            activity = requireActivity()
            adapter = SpecializationListAdapter(get())
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                showBottomSheet(SpecializationListFilterDialogFragment())
            }
        }.root
    }
}