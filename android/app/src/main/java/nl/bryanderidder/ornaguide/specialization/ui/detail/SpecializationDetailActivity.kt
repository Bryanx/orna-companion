package nl.bryanderidder.ornaguide.specialization.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.characterclass.ui.detail.LearnsAdapter
import nl.bryanderidder.ornaguide.characterclass.ui.detail.PassivesAdapter
import nl.bryanderidder.ornaguide.databinding.ActivitySpecializationDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single character class
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class SpecializationDetailActivity :
    BindingActivity<ActivitySpecializationDetailBinding>(R.layout.activity_specialization_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@SpecializationDetailActivity
            activity = this@SpecializationDetailActivity
            learnsAdapter = LearnsAdapter(get())
            passivesAdapter = PassivesAdapter(get())
            boostsAdapter = BoostsAdapter()
            vm = getViewModel<SpecializationDetailViewModel>()
                .apply { loadSpecialization() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<SpecializationDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}