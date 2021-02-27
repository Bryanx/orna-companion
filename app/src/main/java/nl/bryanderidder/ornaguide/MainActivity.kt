package nl.bryanderidder.ornaguide

import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import nl.bryanderidder.ornaguide.characterclass.CharacterClassAdapter
import nl.bryanderidder.ornaguide.databinding.ActivityMainBinding
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import timber.log.Timber.DebugTree


class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()
    val sessionVM: SessionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())

        Timber.d("onCreate");
        binding {
            lifecycleOwner = this@MainActivity
            adapter = CharacterClassAdapter(sessionVM)
            vm = viewModel
        }
    }
}