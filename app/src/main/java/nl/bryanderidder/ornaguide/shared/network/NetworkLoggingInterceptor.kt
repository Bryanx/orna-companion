package nl.bryanderidder.ornaguide.shared.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class NetworkLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request();

        val t1 = System.nanoTime();
        Timber.i(String.format("Sending request to %s%n%s", request.url, request.headers))

        val response = chain.proceed(request);

        val t2 = System.nanoTime();
        Timber.i(String.format("Received response for %s in %.1fms%n%s", response.request.url, (t2 - t1) / 1e6, response.headers));
        return response;
    }
}