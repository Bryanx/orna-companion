package nl.bryanderidder.ornaguide.monster.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentMonsterListBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel


class MonsterListFragment : BindingFragment<FragmentMonsterListBinding>(R.layout.fragment_monster_list) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@MonsterListFragment
            activity = requireActivity()
            adapter = MonsterListAdapter(get())
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                findNavController().navigate(R.id.action_monsterListFragment_to_monsterListFilterDialogFragment)
            }
        }.root
    }
}