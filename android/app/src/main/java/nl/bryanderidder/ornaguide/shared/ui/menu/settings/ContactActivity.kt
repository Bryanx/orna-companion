package nl.bryanderidder.ornaguide.shared.ui.menu.settings

import android.annotation.SuppressLint
import android.os.Bundle
import com.skydoves.bindables.BindingActivity
import nl.bryanderidder.ornaguide.*
import nl.bryanderidder.ornaguide.databinding.ActivityContactBinding
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil

@SuppressLint("MissingSuperCall")
class ContactActivity : BindingActivity<ActivityContactBinding>(R.layout.activity_contact) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding {
            lifecycleOwner = this@ContactActivity
            activity = this@ContactActivity
            discordLink.setOnClickListener {
                NetworkUtil.goToUrl(this@ContactActivity, getString(R.string.orna_discord_url))
            }
            ornaGuideLink.setOnClickListener {
                NetworkUtil.goToUrl(this@ContactActivity, getString(R.string.ornaguide_url))
            }
        }
    }
}