package nl.bryanderidder.ornaguide.item.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityItemDetailBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * Activity for a single item
 * @author Bryan de Ridder
 */
@SuppressLint("MissingSuperCall")
class ItemDetailActivity :
    BindingActivity<ActivityItemDetailBinding>(R.layout.activity_item_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        TransformationCompat.onTransformationEndContainerApplyParams(this)
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@ItemDetailActivity
            activity = this@ItemDetailActivity
            itemEquippedByAdapter = ItemEquippedByAdapter(get())
            itemDroppedByAdapter = ItemDroppedByAdapter(get())
            itemMaterialsAdapter = ItemMaterialsAdapter(get())
            itemQuestsAdapter = ItemQuestsAdapter(get())
            vm = getViewModel<ItemDetailViewModel>()
                .apply { loadItem() }
        }
    }

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<ItemDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}