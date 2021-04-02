package nl.bryanderidder.ornaguide.achievement.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityAchievementDetailBinding
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single achievement
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class AchievementDetailActivity :
    BindingActivity<ActivityAchievementDetailBinding>(R.layout.activity_achievement_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@AchievementDetailActivity
            activity = this@AchievementDetailActivity
            vm = getViewModel<AchievementDetailViewModel>()
                .apply { loadAchievement() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<AchievementDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}