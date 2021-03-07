package nl.bryanderidder.ornaguide.pet.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityPetDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single pet
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class PetDetailActivity :
    BindingActivity<ActivityPetDetailBinding>(R.layout.activity_pet_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@PetDetailActivity
            activity = this@PetDetailActivity
            skillsAdapter = PetSkillsAdapter(get())
            vm = getViewModel<PetDetailViewModel>()
                .apply { loadPet() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<PetDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}