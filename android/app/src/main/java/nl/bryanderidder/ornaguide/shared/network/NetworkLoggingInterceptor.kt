package nl.bryanderidder.ornaguide.shared.network

import nl.bryanderidder.ornaguide.shared.util.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber

/**
 * Intercept each network request and log the events.
 *
 * @author Bryan de Ridder
 */
class NetworkLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val t1 = System.nanoTime()
        Timber.i(String.format("sendNetworkRequest {\"url\":%s,\"body\":%s}", request.url, NetworkUtil.requestBodyToString(request.body)))

        val response = chain.proceed(request)

        val t2 = System.nanoTime()

        Timber.i("receiveNetworkResponse ${JSONObject()
            .put("url", response.request.url)
            .put("responseTime", (t2 - t1) / 1e6)
            .put("code", response.code)
            .toString()
            .replace("\\","")}")

        return response
    }
}