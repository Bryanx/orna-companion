package nl.bryanderidder.ornaguide.shared.ui.alerts

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.firebase.crashlytics.FirebaseCrashlytics
import nl.bryanderidder.ornaguide.R
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil


/**
 * All alert dialogs
 * @author Bryan de Ridder
 */

fun Context.showCrashReportsDialog(sharedPrefs: SharedPrefsUtil) {
    if (!sharedPrefs.isCrashReportsEnabled() && !sharedPrefs.isCrashReportsReminderShown())
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.crash_reports_dialog_title))
            .setMessage(getString(R.string.crash_reports_dialog_description))
            .setPositiveButton(R.string.yes) { _, _ ->
                FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
                sharedPrefs.setCrashReportsEnabled(true)
                sharedPrefs.setCrashReportsReminderShown()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
                sharedPrefs.setCrashReportsEnabled(false)
                sharedPrefs.setCrashReportsReminderShown()
            }
            .setIcon(R.drawable.ic_baseline_bug_report_24)
            .show()
}