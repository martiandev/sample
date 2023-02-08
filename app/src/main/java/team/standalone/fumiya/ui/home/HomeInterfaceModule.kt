package team.standalone.fumiya.ui.home

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView

class HomeInterfaceModule(
    private val homeViewModel: HomeViewModel,
    private val context: Context,
    val webView: WebView,
) {

    /**
     * サインアップ・ログインボタン
     */
    @JavascriptInterface
    fun signup() {

    }

    @JavascriptInterface
    fun getUserLoginStatus() {

    }

}