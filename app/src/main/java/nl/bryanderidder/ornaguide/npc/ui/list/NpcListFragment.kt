package nl.bryanderidder.ornaguide.npc.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentNpcListBinding
import nl.bryanderidder.ornaguide.npc.ui.list.filter.NpcListFilterDialogFragment
import nl.bryanderidder.ornaguide.shared.util.showBottomSheet
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel


class NpcListFragment : BindingFragment<FragmentNpcListBinding>(R.layout.fragment_npc_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@NpcListFragment
            activity = requireActivity()
            adapter = NpcListAdapter(get())
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                showBottomSheet(NpcListFilterDialogFragment())
            }
        }.root
    }
}