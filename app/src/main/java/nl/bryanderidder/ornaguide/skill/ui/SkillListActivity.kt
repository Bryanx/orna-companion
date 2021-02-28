package nl.bryanderidder.ornaguide.skill.ui

import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassActivity
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassAdapter
import nl.bryanderidder.ornaguide.databinding.ActivitySkillBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Displays a list of skills
 * @author Bryan de Ridder
 */
class SkillListActivity :
    BindingActivity<ActivitySkillBinding>(R.layout.activity_skill) {

    val viewModel: SkillListViewModel by viewModel()
    val sessionVM: SessionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@SkillListActivity
            adapter = SkillListAdapter(sessionVM)
            vm = viewModel
        }
    }
}