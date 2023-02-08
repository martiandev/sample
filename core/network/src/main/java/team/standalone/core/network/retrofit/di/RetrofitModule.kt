package team.standalone.core.network.retrofit.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import team.standalone.core.common.qualifier.HttpCache
import team.standalone.core.common.qualifier.HttpCacheFile
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.network.BuildConfig
import team.standalone.core.network.retrofit.adapter.InstantAdapter
import team.standalone.core.network.retrofit.authenticator.TokenAuthenticator
import team.standalone.core.network.retrofit.interceptor.ClientInterceptor
import team.standalone.core.network.retrofit.interceptor.NetworkStatusInterceptor
import team.standalone.core.network.retrofit.interceptor.TokenInterceptor
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {
    @HttpCacheFile
    @Singleton
    @Provides
    fun cacheFile(@ApplicationContext context: Context): File {
        return File(context.cacheDir, "http-cache")
    }

    @HttpCache
    @Singleton
    @Provides
    fun cache(@HttpCacheFile file: File): Cache? {
        return try {
            val cacheSize: Long = 20 * 1024 * 1024
            Cache(file, cacheSize)
        } catch (e: Exception) {
            null
        }
    }

    @Singleton
    @Provides
    fun okHttpClient(
        @HttpCache cache: Cache?,
        networkStatusInterceptor: NetworkStatusInterceptor,
        tokenInterceptor: TokenInterceptor,
        clientInterceptor: ClientInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(clientInterceptor)
            .addInterceptor(HttpLoggingInterceptor { message -> Lumberjack.debug(message) }
                .apply {
                    level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                })
            .authenticator(tokenAuthenticator)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    fun moshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(InstantAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun converterFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun retrofit(
        endpoint: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}