package team.standalone.core.common.util

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import team.standalone.core.common.extension.visible

open class CustomWebViewClient : WebViewClient() {

    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        softVisible(view)
        if(isReceivedError(error?.errorCode)) {
            view.loadUrl(ERROR)
        }
    }

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        return false
    }

    /**
     * Check if error code is related to network or URL issues.
     * */
    open fun isReceivedError(errorCode: Int?): Boolean = when (errorCode) {
        ERROR_HOST_LOOKUP, ERROR_BAD_URL -> true
        else -> false
    }

    /**
     * Include visibility delay for soft transition.
     * */
    open fun softVisible(view: WebView) {
        view.postDelayed({ view.visible() }, INTERVAL)
    }

    companion object {
        const val INTERVAL: Long = 100
        const val ERROR = "about:blank"
        const val VIDEO_TITLE = "VIDEO_TITLE"
        const val VIDEO_URL = "VIDEO_URL"
        const val VIDEO_LINK = "standalone.dsign.gift/video/"
    }
}