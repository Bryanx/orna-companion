package nl.bryanderidder.ornaguide.shared.logging

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber


/**
 * Custom Timber log tree that reports Log.ERROR logs to crashlytics
 * @author Bryan de Ridder
 */
class CrashlyticsTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.WARN)
            return

        val t = throwable ?: Exception(message)

        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCustomKey(CRASHLYTICS_KEY_PRIORITY, priority)
        crashlytics.setCustomKey(CRASHLYTICS_KEY_TAG, tag ?: "Unknown")
        crashlytics.setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
        crashlytics.recordException(t)
    }

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}