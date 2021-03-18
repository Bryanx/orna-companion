package nl.bryanderidder.ornaguide.monster.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogMonsterFilterSpawnBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class MonsterListFilterSpawnFragment : BindingFragment<FragmentDialogMonsterFilterSpawnBinding>(R.layout.fragment_dialog_monster_filter_spawn) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@MonsterListFilterSpawnFragment
            vm = getSharedViewModel()
        }.root
    }
}