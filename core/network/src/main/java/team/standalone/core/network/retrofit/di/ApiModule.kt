package team.standalone.core.network.retrofit.di

import com.google.common.net.HttpHeaders
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import team.standalone.core.common.qualifier.Chat
import team.standalone.core.common.qualifier.TimeoutConnect
import team.standalone.core.common.qualifier.TimeoutRead
import team.standalone.core.common.qualifier.UserAgent
import team.standalone.core.network.retrofit.api.ChatApi
import team.standalone.core.network.retrofit.util.ContentTypeKeys.APPLICATION_FORM_URLENCODED
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun providesChatApi(
        @UserAgent userAgent: String,
        @Chat chatUrl: String,
        @TimeoutConnect timeoutConnect: Long,
        @TimeoutRead timeoutRead: Long
    ): ChatApi {
        val retryOnConnectionFailure = false
        val httpClient = OkHttpClient.Builder()
            .readTimeout(timeoutRead, TimeUnit.SECONDS)
            .connectTimeout(timeoutConnect, TimeUnit.SECONDS)
            .retryOnConnectionFailure(retryOnConnectionFailure)
            .addInterceptor{ chain ->
                val headerUserAgent = "$userAgent okhttp"
                val requestBuilder = chain.request().newBuilder()
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_FORM_URLENCODED)
                    .header(HttpHeaders.USER_AGENT, headerUserAgent)
                chain.proceed(requestBuilder.build())
            }
        return RetrofitModule.retrofit(
            chatUrl,
            httpClient.build(),
            GsonConverterFactory.create()
        ).create(ChatApi::class.java)
    }
}