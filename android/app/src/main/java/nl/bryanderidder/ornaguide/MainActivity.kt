package nl.bryanderidder.ornaguide

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.skydoves.bindables.BindingActivity
import com.skydoves.transformationlayout.onTransformationStartContainer
import nl.bryanderidder.ornaguide.databinding.ActivityMainBinding
import nl.bryanderidder.ornaguide.shared.util.color


@SuppressLint("MissingSuperCall")
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        window.statusBarColor = color(R.color.backgroundColorDark)
        setUpNavigation()
    }

    fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)
    }
}