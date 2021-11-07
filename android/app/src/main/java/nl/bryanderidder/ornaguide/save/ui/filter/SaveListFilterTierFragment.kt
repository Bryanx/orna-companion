package nl.bryanderidder.ornaguide.save.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogSaveFilterTierBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class SaveListFilterTierFragment : BindingFragment<FragmentDialogSaveFilterTierBinding>(R.layout.fragment_dialog_save_filter_tier) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SaveListFilterTierFragment
            vm = getSharedViewModel()
        }.root
    }
}