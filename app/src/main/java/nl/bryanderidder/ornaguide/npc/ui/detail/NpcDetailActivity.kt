package nl.bryanderidder.ornaguide.npc.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityNpcDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single npc
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class NpcDetailActivity :
    BindingActivity<ActivityNpcDetailBinding>(R.layout.activity_npc_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@NpcDetailActivity
            activity = this@NpcDetailActivity
            npcQuestsAdapter = NpcQuestsAdapter(get())
            vm = getViewModel<NpcDetailViewModel>()
                .apply { loadNpc() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<NpcDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}