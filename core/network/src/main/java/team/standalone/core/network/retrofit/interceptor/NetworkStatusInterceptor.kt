package team.standalone.core.network.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class NetworkStatusInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}