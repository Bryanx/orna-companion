package nl.bryanderidder.ornaguide.characterclass.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityCharacterClassDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single character class
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class CharacterClassDetailActivity :
    BindingActivity<ActivityCharacterClassDetailBinding>(R.layout.activity_character_class_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@CharacterClassDetailActivity
            activity = this@CharacterClassDetailActivity
            learnsAdapter = LearnsAdapter(get())
            passivesAdapter = PassivesAdapter(get())
            vm = getViewModel<CharacterClassDetailViewModel>()
                .apply { loadCharacterClass() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<CharacterClassDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}