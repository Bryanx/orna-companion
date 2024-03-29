package nl.bryanderidder.ornaguide.shared.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import okhttp3.RequestBody
import okio.Buffer
import okio.IOException
import timber.log.Timber
import java.net.UnknownHostException

object NetworkUtil {
    fun hasConnection(context: Context): Boolean {
        var status = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null)
            status = true
        return status
    }

    fun requestBodyToString(request: RequestBody?): String? {
        if (request == null) return ""
        return try {
            val buffer = Buffer()
            request.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            Timber.e("Could not read requestbody")
            "{}"
        }
    }

    fun goToUrl(activity: Activity, url: String) {
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        launchBrowser.addCategory(Intent.CATEGORY_BROWSABLE)
        activity.startActivity(launchBrowser)
    }

    fun handleExceptionWithNetworkMessage(onError: (String?) -> Unit, exception: Throwable) =
        handleException(onError, exception, onNoNetwork = "No internet connection")

    fun handleException(
        onError: (String?) -> Unit,
        exception: Throwable,
        onNoNetwork: String = "",
    ) {
        if (exception !is UnknownHostException) {
            Timber.e(exception)
            onError.invoke(exception.localizedMessage)
        } else onError.invoke(onNoNetwork)
    }
}