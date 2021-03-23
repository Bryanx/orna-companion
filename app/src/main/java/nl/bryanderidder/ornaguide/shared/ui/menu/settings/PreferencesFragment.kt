package nl.bryanderidder.ornaguide.shared.ui.menu.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mikepenz.aboutlibraries.LibsBuilder
import nl.bryanderidder.ornaguide.MainActivity
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.ui.menu.sync.SyncActivity

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val preferenceManager = preferenceManager
        preferenceManager.sharedPreferencesName = context?.packageName
        addPreferencesFromResource(R.xml.settings)
        onClick("syncWithOrnaGuide") {
            startActivity(Intent(context, SyncActivity::class.java))
            (requireActivity() as MainActivity).finish()
        }
        onClick("license") { LibsBuilder().start(requireContext()) }
    }

    private fun onClick(key: String, action: () -> Unit) {
        findPreference<Preference>(key)?.setOnPreferenceClickListener {
            action.invoke().run { true }
        }
    }
}