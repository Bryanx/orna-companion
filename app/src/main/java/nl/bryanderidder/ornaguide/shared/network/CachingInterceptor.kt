package nl.bryanderidder.ornaguide.shared.network

import android.content.Context
import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.*


class CachingInterceptor(
    private val sharedPrefsUtil: SharedPrefsUtil,
    private val context: Context
) : Interceptor {

    /**
     * For each unique body + path we register the request time and body+path
     * If the current time is 15 minutes passed the last request time and body+path match
     * we allow the request otherwise we don't.
     *
     * This is hacky fix to cache POST requests.
     */
    override fun intercept(chain: Interceptor.Chain): Response {

        // if no connection block the request.
        if (!NetworkUtil.hasConnection(context))
            return getEmptyResponse(chain)

        val request = chain.request()

        // don't cache item assess
        if (request.url.toString().contains("assess"))
            return chain.proceed(request)

        val bodyAndPath = NetworkUtil.requestBodyToString(request.body) + request.url.encodedPath
        val currentTime = Calendar.getInstance().timeInMillis

        // cancel request if TTL has not passed since last request
        val elapsedTimeSinceLastRequest = currentTime - sharedPrefsUtil.readLong(bodyAndPath)
        if (elapsedTimeSinceLastRequest < CACHE_TTL)
            return getEmptyResponse(chain)

        // TTL has passed, request can pass.
        sharedPrefsUtil.writeLong(bodyAndPath, currentTime)
        return chain.proceed(request)
    }

    /**
     * Create fake empty response to prevent setting new data
     */
    private fun getEmptyResponse(chain: Interceptor.Chain) = Response.Builder()
        .code(200)
        .body("[]".toResponseBody())
        .protocol(Protocol.HTTP_2)
        .message("OK")
        .request(chain.request())
        .build()

    companion object {
        const val CACHE_TTL = 3600000 // 1 hour
    }
}
