package nl.bryanderidder.ornaguide.skill.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivitySkillDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single character class
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class SkillDetailActivity :
    BindingActivity<ActivitySkillDetailBinding>(R.layout.activity_skill_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@SkillDetailActivity
            activity = this@SkillDetailActivity
            learnedByAdapter = LearnedByAdapter(get())
            monstersUseAdapter = MonstersUseAdapter(get())
            petsUseAdapter = PetsUseAdapter(get())
            buffedByAdapter = BuffedByAdapter(get())
            vm = getViewModel<SkillDetailViewModel>()
                .apply { loadSkill() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<SkillDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}