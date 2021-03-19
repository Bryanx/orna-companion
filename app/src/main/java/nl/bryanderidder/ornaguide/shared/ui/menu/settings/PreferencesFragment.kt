package nl.bryanderidder.ornaguide.shared.ui.menu.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.mikepenz.aboutlibraries.LibsBuilder
import nl.bryanderidder.ornaguide.R

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val preferenceManager = preferenceManager
        preferenceManager.sharedPreferencesName = context?.packageName
        addPreferencesFromResource(R.xml.settings)
        onClick("license") { LibsBuilder().start(requireContext()) }
    }

    private fun onClick(key: String, action: () -> Unit) {
        findPreference<Preference>(key)?.setOnPreferenceClickListener {
            action.invoke().run { true }
        }
    }
}