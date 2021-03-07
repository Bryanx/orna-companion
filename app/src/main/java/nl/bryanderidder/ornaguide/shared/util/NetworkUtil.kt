package nl.bryanderidder.ornaguide.shared.util

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.RequestBody
import okio.Buffer
import okio.IOException
import timber.log.Timber

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
}