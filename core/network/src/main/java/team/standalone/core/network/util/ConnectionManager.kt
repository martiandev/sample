package team.standalone.core.network.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import dagger.hilt.android.qualifiers.ApplicationContext
import team.standalone.core.common.extension.orFalse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionManager @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    private val connectivityManager =
        appContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkConnected(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasCapability(NET_CAPABILITY_INTERNET).orFalse()
    }
}