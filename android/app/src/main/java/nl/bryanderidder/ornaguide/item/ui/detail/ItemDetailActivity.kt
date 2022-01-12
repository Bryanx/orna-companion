package nl.bryanderidder.ornaguide.item.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivityItemDetailBinding
import nl.bryanderidder.ornaguide.item.model.Item
import nl.bryanderidder.ornaguide.item.ui.detail.assess.ItemAssessFragment
import nl.bryanderidder.ornaguide.item.ui.detail.assess.ItemAssessHistoryFragment
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
            vm = getViewModel<ItemDetailViewModel>().apply { loadItem {
                val tabs = getTabs(it)
                val pager = ItemDetailPagerAdapter(supportFragmentManager, lifecycle, tabs)
                viewPager.adapter = pager
                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                    tab.text = pager.labels[position]
                }.attach()
                if (tabs.size > 1)
                    viewPager.setCurrentItem(1, false)
            } }
        }
    }

    private fun getTabs(item: Item): Map<String, () -> Fragment> =
        if (item.isEquipmentType())
            mapOf<String, () -> Fragment>(
                "Assess" to ::ItemAssessFragment,
                "Details" to ::ItemDetailFragment,
                "History" to ::ItemAssessHistoryFragment)
        else
            mapOf<String, () -> Fragment>("Details" to ::ItemDetailFragment)

    companion object {
        fun startActivity(transformationLayout: TransformationLayout) =
            transformationLayout.context.intentOf<ItemDetailActivity> {
                TransformationCompat.startActivity(transformationLayout, intent)
            }
    }
}