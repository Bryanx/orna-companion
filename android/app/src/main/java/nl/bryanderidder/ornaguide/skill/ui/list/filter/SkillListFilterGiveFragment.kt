package nl.bryanderidder.ornaguide.skill.ui.list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.bindables.BindingFragment
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.FragmentDialogSkillFilterGiveBinding
import org.koin.android.viewmodel.ext.android.getSharedViewModel

class SkillListFilterGiveFragment : BindingFragment<FragmentDialogSkillFilterGiveBinding>(R.layout.fragment_dialog_skill_filter_give) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            lifecycleOwner = this@SkillListFilterGiveFragment
            vm = getSharedViewModel()
        }.root
    }
}