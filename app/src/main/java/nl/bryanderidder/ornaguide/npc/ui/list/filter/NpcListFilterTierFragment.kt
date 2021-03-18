package nl.bryanderidder.ornaguide.npc.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogNpcFilterTierBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class NpcListFilterTierFragment : BindingFragment<FragmentDialogNpcFilterTierBinding>(R.layout.fragment_dialog_npc_filter_tier) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@NpcListFilterTierFragment
            vm = getSharedViewModel()
        }.root
    }
}