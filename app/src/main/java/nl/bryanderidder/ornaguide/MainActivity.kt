package nl.bryanderidder.ornaguide

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.skydoves.bindables.BindingActivity
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.databinding.ActivityMainBinding
import timber.log.Timber
import timber.log.Timber.DebugTree


@SuppressLint("MissingSuperCall")
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())
        setUpNavigation();
    }

    fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)
    }
}