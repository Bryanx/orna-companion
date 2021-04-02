package nl.bryanderidder.ornaguide.shared.ui.menu.sync

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import nl.bryanderidder.ornaguide.MainActivity
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.databinding.ActivitySyncBinding
import nl.bryanderidder.ornaguide.shared.util.color
import org.koin.android.viewmodel.ext.android.getViewModel

@SuppressLint("MissingSuperCall")
class SyncActivity : BindingActivity<ActivitySyncBinding>(R.layout.activity_sync) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = color(R.color.backgroundColorDark)
        binding {
            lifecycleOwner = this@SyncActivity
            activity = this@SyncActivity
            vm = getViewModel()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}