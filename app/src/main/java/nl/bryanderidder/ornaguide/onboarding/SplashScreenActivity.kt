package nl.bryanderidder.ornaguide.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.bryanderidder.ornaguide.MainActivity
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import org.koin.android.ext.android.get


/**
 * Main entry point
 * @author Bryan de Ridder
 */
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (get<SharedPrefsUtil>().isFirstStart())
            startActivity(Intent(this, OnboardingActivity::class.java))
        else
            startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}