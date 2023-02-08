package team.standalone.core.common.util

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Message
import android.webkit.WebChromeClient
import android.webkit.WebView

class CustomWebChromeClient : WebChromeClient() {
    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        view?.let { it ->
            val result = it.hitTestResult
            val data = result.extra
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data))
            view.context.startActivity(browserIntent)
        }
        return false
    }

    override fun getDefaultVideoPoster(): Bitmap? {
        return Bitmap.createBitmap(FILE_SIZE, FILE_SIZE, Bitmap.Config.ARGB_8888)
    }

    companion object {
        const val FILE_SIZE = 10
    }
}