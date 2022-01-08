package nl.bryanderidder.ornaguide.monster.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogMonsterFilterImmuneToStatusBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class MonsterListFilterImmuneToStatusFragment : BindingFragment<FragmentDialogMonsterFilterImmuneToStatusBinding>(R.layout.fragment_dialog_monster_filter_immune_to_status) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@MonsterListFilterImmuneToStatusFragment
            vm = getSharedViewModel()
        }.root
    }
}