package nl.bryanderidder.ornaguide.skill.ui.list

import android.os.Bundle
import android.view.View
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentSkillListBinding
import nl.bryanderidder.ornaguide.shared.util.navigateSafely
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getSharedViewModel

/**
 * Displays a list of skills
 * @author Bryan de Ridder
 */
class SkillListFragment :
    BindingFragment<FragmentSkillListBinding>(R.layout.fragment_skill_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            lifecycleOwner = this@SkillListFragment
            activity = requireActivity()
            adapter = SkillListAdapter(get())
            vm = getSharedViewModel()
            filterFab.setOnClickListener {
                navigateSafely(R.id.action_skillListFragment_to_skillListFilterDialogFragment)
            }
        }.root
    }
}