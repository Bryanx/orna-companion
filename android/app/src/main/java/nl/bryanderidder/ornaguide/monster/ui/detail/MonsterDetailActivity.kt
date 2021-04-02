package nl.bryanderidder.ornaguide.monster.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityMonsterDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single monster
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class MonsterDetailActivity :
    BindingActivity<ActivityMonsterDetailBinding>(R.layout.activity_monster_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@MonsterDetailActivity
            activity = this@MonsterDetailActivity
            monsterSkillsAdapter = MonsterSkillsAdapter(get())
            monsterDropsAdapter = MonsterDropsAdapter(get())
            monsterQuestsAdapter = MonsterQuestsAdapter(get())
            vm = getViewModel<MonsterDetailViewModel>()
                .apply { loadMonster() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<MonsterDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}