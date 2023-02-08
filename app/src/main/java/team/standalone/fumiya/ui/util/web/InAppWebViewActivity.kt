package team.standalone.fumiya.ui.util.web

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.ActivityInappWebviewBinding
import timber.log.Timber

/**
 * "standalone://local.web/?url=http://hoge.fuga"
 */
class InAppWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInappWebviewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inapp_webview)

        val toolbar = binding.toolbar
        toolbar.setTitleTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.textColorPrimary
            )
        )
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""
            setSupportActionBar(toolbar)
            setHomeAsUpIndicator(R.drawable.ic_menu_ico_arrow_left)
            setDisplayHomeAsUpEnabled(true)
        }

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_inapp_webview, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> onBackPressed()
                    R.id.btn_webview_finish -> finish()
                }
                return true
            }

        })
        contentView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun contentView() {
        val url = intent.getStringExtra(URL_WEB)
        if (url == null  || url == "") {
            finish()
            return
        }
        binding.inAppWebview.setBackgroundColor(0)
        lifecycleScope.launch {
            binding.inAppWebview.apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.setSupportMultipleWindows(true)
            }
            binding.inAppWebview.loadUrl(url)
        }

        // _target = new の時に外部で開く仕組み
        binding.inAppWebview.webChromeClient =
            object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    val result = view!!.hitTestResult
                    val data = result.extra
                    Timber.v("LOG_DEBUG onCreateWindow ${result.extra}")
                    if (result.extra == null) return true
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data))
                    view.context.startActivity(browserIntent)
                    return false
                }
            }

        binding.inAppWebview.webViewClient =
            object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    Timber.v("LOG_DEBUG onPageFinished $url")
                    if (url?.startsWith("file:///") as Boolean) {
                        // エラーページ
                        return
                    }
                    supportActionBar?.title = binding.inAppWebview.title
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Timber.e("LOG_DEBUG load url error ${error?.errorCode}")
                    if (request?.isForMainFrame as Boolean) {
                        if (error?.errorCode!! < 0) {
                            // 自作のエラーページを表示
                            view?.loadUrl("file:///android_asset/error.html")
                        }
                    }
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    Timber.v("LOG_DEBUG shouldOverrideUrlLoading ${request?.url}")
                    Timber.v("LOG_DEBUG ${request?.url?.authority}")
                    return false
                }
            }
    }

    override fun onBackPressed() {
        if (binding.inAppWebview.canGoBack()) {
            binding.inAppWebview.goBack()
            return
        }
        super.onBackPressed()
    }

    companion object {
        const val URL_WEB = "URL_WEB"
    }
}