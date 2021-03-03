package nl.bryanderidder.ornaguide.npc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentNpcListBinding
import org.koin.android.viewmodel.ext.android.getViewModel


class NpcListFragment : BindingFragment<FragmentNpcListBinding>(R.layout.fragment_npc_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@NpcListFragment
            adapter = NpcListAdapter(getViewModel())
            vm = getViewModel()
        }.root
    }
}