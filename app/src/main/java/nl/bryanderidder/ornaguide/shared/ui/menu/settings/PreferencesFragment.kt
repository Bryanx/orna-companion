package nl.bryanderidder.ornaguide.shared.ui.menu.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.play.core.review.ReviewManagerFactory
import com.mikepenz.aboutlibraries.LibsBuilder
import nl.bryanderidder.ornaguide.MainActivity
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.ui.menu.sync.SyncActivity
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
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
        onClick("license") { LibsBuilder().start(requireContext()) }
        onClick("rate") { requestReview() }
    }

    private fun requestReview() {
        try {
            val manager = ReviewManagerFactory.create(requireContext())
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> manager.launchReviewFlow(requireActivity(), task.result)
                    else -> NetworkUtil.goToUrl(requireActivity(), getString(R.string.play_store_url))
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
}