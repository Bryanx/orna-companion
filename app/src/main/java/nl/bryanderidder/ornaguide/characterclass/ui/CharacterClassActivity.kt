package nl.bryanderidder.ornaguide.characterclass.ui

import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityCharacterClassBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Activity for a single character class
 * @author Bryan de Ridder
 */
class CharacterClassActivity :
    BindingActivity<ActivityCharacterClassBinding>(R.layout.activity_character_class) {

    val sessionVM: SessionViewModel by viewModel()
    val viewModel: CharacterClassViewModel by viewModel { parametersOf(sessionVM) }

    override fun onCreate(savedInstanceState: Bundle?) {
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@CharacterClassActivity
            vm = sessionVM
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<CharacterClassActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}