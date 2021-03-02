package nl.bryanderidder.ornaguide

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.skydoves.bindables.BindingActivity
import nl.bryanderidder.ornaguide.databinding.ActivityMainBinding
import nl.bryanderidder.ornaguide.shared.ui.MainPagerAdapter
import nl.bryanderidder.ornaguide.shared.ui.MenuFragment
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
        initializeUI()
    }

    private fun initializeUI() {
        with(binding.mainViewpager) {
            adapter = MainPagerAdapter(supportFragmentManager, lifecycle)
            offscreenPageLimit = 2
//            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageScrollStateChanged(state: Int) = Unit
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) = Unit
//
//                override fun onPageSelected(position: Int) {
//                }
//            })
            binding.menuFab.setOnClickListener {
                MenuFragment().also {
                    it.show(supportFragmentManager, it.tag)
                }
            }
        }
    }
}