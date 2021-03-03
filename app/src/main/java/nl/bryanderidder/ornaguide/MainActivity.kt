package nl.bryanderidder.ornaguide

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skydoves.bindables.BindingActivity
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.databinding.ActivityMainBinding
import nl.bryanderidder.ornaguide.shared.ui.MainPagerAdapter
import nl.bryanderidder.ornaguide.shared.util.dp
import nl.bryanderidder.ornaguide.shared.util.onPageSelected
import nl.bryanderidder.ornaguide.shared.util.onSlide
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
        initializeUI()
    }

    private fun initializeUI() = with(binding) {
        mainViewpager.adapter = MainPagerAdapter(supportFragmentManager, lifecycle)
        mainViewpager.offscreenPageLimit = 3
        val sheetBehavior = BottomSheetBehavior.from(includeMenu.bottomMenuContainer)
        mainViewpager.onPageSelected {
            toolbarTitle = includeMenu.bottomMenu.getName(mainViewpager.currentItem)
            includeMenu.bottomMenu.setSelectedItem(mainViewpager.currentItem)
        }
        sheetBehavior.isFitToContents = true
        sheetBehavior.peekHeight = 56.dp
        sheetBehavior.onSlide { offset -> bottomSheetBackground.alpha = offset }
        includeMenu.bottomMenu.setSelectedItem(mainViewpager.currentItem)
        toolbarTitle = includeMenu.bottomMenu.getName(mainViewpager.currentItem)
        includeMenu.bottomMenu.getName(mainViewpager.currentItem)
        includeMenu.bottomMenu.setOnItemSelectedListener { _, index ->
            mainViewpager.currentItem = index
            sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        includeMenu.menuBtn.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                sheetBehavior.state =BottomSheetBehavior.STATE_EXPANDED
        }
    }
}