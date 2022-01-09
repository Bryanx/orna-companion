package nl.bryanderidder.ornaguide.skill.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogSkillFilterSourceBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class SkillListFilterSourceFragment : BindingFragment<FragmentDialogSkillFilterSourceBinding>(R.layout.fragment_dialog_skill_filter_source) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SkillListFilterSourceFragment
            vm = getSharedViewModel()
        }.root
    }
}