package nl.bryanderidder.ornaguide.skill.ui

import android.os.Bundle
import android.view.View
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentSkillListBinding
import org.koin.android.viewmodel.ext.android.getViewModel


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
            adapter = SkillListAdapter(getViewModel())
            vm = getViewModel()
        }.root
    }
}