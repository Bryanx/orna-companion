package nl.bryanderidder.ornaguide.characterclass

import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationCompat.onTransformationEndContainerApplyParams
import com.skydoves.transformationlayout.TransformationLayout
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityCharacterClassBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Activity for a single character class
 * @author Bryan de Ridder
 */
class CharacterClassActivity :
    BindingActivity<ActivityCharacterClassBinding>(R.layout.activity_character_class) {

    val sessionVM: SessionViewModel by viewModel()
    val viewModel: CharacterClassViewModel by inject { parametersOf(sessionVM)}

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@CharacterClassActivity
            characterClass = sessionVM.characterClass.value
            vm = viewModel
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<CharacterClassActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}