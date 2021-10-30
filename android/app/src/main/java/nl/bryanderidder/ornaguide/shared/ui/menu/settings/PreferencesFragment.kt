package nl.bryanderidder.ornaguide.shared.ui.menu.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mikepenz.aboutlibraries.LibsBuilder
import nl.bryanderidder.ornaguide.MainActivity
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.ui.menu.sync.SyncActivity
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import nl.bryanderidder.ornaguide.shared.util.navigateSafely
import timber.log.Timber

class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val preferenceManager = preferenceManager
        preferenceManager.sharedPreferencesName = context?.packageName
        addPreferencesFromResource(R.xml.settings)
        onClick("syncWithOrnaGuide") {
            startActivity(Intent(context, SyncActivity::class.java))
            (requireActivity() as MainActivity).finish()
        }
        onClick("license") {
            LibsBuilder().start(requireContext())
        }
        onClick("rate") {
            requestReview()
        }
        onClickSwitch("CRASH_REPORTS") {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(it)
        }
        onClick("contact") {
            navigateSafely(R.id.action_settingsFragment_to_contactActivity)
        }
    }

    private fun requestReview() {
        try {
            val manager = ReviewManagerFactory.create(requireContext())
            val request = manager.requestReviewFlow()
            val activity = requireActivity()
            request.addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> manager.launchReviewFlow(activity, task.result)
                    else -> NetworkUtil.goToUrl(activity, getString(R.string.play_store_url))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Something went wrong trying to start in-app review flow.")
            NetworkUtil.goToUrl(requireActivity(), getString(R.string.play_store_url))
        }
    }

    private fun onClick(key: String, action: () -> Unit) {
        findPreference<Preference>(key)?.setOnPreferenceClickListener {
            action.invoke().run { true }
        }
    }

    private fun onClickSwitch(key: String, action: (Boolean) -> Unit) {
        findPreference<SwitchPreference>(key)?.setOnPreferenceClickListener {
            action.invoke((it as SwitchPreference).isChecked).run { true }
        }
    }
}