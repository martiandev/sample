package team.standalone.fumiya.ui.park.video

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.gone
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.qualifier.UserAgent
import team.standalone.core.common.util.CustomWebChromeClient.Companion.FILE_SIZE
import team.standalone.core.common.util.CustomWebViewClient
import team.standalone.core.common.util.CustomWebViewClient.Companion.VIDEO_TITLE
import team.standalone.core.common.util.CustomWebViewClient.Companion.VIDEO_URL
import team.standalone.core.ui.androidcomponent.BaseActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.ActivityVideoBinding
import javax.inject.Inject

@AndroidEntryPoint
class VideoActivity : BaseActivity<ActivityVideoBinding>() {

    @Inject
    @UserAgent
    lateinit var userAgent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = withDataBinding(R.layout.activity_video)

        val url = intent.getStringExtra(VIDEO_URL)
        val titleName = intent.getStringExtra(VIDEO_TITLE)
        if (url == null) {
            finish()
            return
        }

        val toolbar = dataBinding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = when (titleName.isNullOrBlank()) {
                true -> getString(R.string.title_video)
                else -> titleName
            }
            setSupportActionBar(toolbar)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_ico_arrow_left)
        }

        displayWebView(url)
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Set web view for video with orientation settings.
     * */
    @SuppressLint("SetJavaScriptEnabled")
    private fun displayWebView(url: String) {
        dataBinding.videoView.apply {
            settings.apply {
                domStorageEnabled = true
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                userAgentString = "$userAgentString$userAgent"
            }
            setBackgroundColor(Color.TRANSPARENT)
            webViewClient = object: CustomWebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    dataBinding.progressBar.gone()
                    view?.let { softVisible(it) }
                    setupChatInputStyle()
                }
            }
            webChromeClient = object: WebChromeClient() {
                var mCustomView: View? = null
                var mCustomViewCallback: CustomViewCallback? = null
                private var mOriginalOrientation: Int = 0
                private var mOriginalSystemUiVisibility: Int = 0
                private val FULL_SCREEN_SETTING = (View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE)

                override fun onHideCustomView() {
                    (window.decorView as FrameLayout).removeView(this.mCustomView)
                    this.mCustomView = null
                    window.decorView.systemUiVisibility = this.mOriginalSystemUiVisibility
                    requestedOrientation = this.mOriginalOrientation
                    this.mCustomViewCallback?.onCustomViewHidden()
                    this.mCustomViewCallback = null
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                    dataBinding.videoView.clearFocus()
                }

                override fun onShowCustomView(
                    paramView: View,
                    paramCustomViewCallback: CustomViewCallback
                ) {
                    if (this.mCustomView != null) {
                        onHideCustomView()
                        return
                    }
                    this.mCustomView = paramView
                    this.mOriginalSystemUiVisibility = window.decorView.systemUiVisibility
                    this.mOriginalOrientation = requestedOrientation
                    this.mCustomViewCallback = paramCustomViewCallback
                    (window
                        .decorView as FrameLayout)
                        .addView(
                            this.mCustomView,
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        )
                    window.decorView.systemUiVisibility = FULL_SCREEN_SETTING
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                    mCustomView!!.setOnSystemUiVisibilityChangeListener { updateControls() }
                }

                override fun getDefaultVideoPoster(): Bitmap? {
                    return Bitmap.createBitmap(FILE_SIZE, FILE_SIZE, Bitmap.Config.ARGB_8888)
                }

                private fun updateControls() {
                    val params = mCustomView?.layoutParams as FrameLayout.LayoutParams
                    params.bottomMargin = 0
                    params.topMargin = 0
                    params.leftMargin = 0
                    params.rightMargin = 0
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    this.mCustomView!!.layoutParams = params
                    window.decorView.systemUiVisibility = FULL_SCREEN_SETTING
                }
            }
            loadUrl(url)
        }
    }

    /**
     * Correction for chatInput responsive layout on keyboard visible.
     * */
    private fun setupChatInputStyle() {
        val chatArea = ".chatArea{padding-bottom:50px}"
        val chatInput = ".chatInput{position:fixed;bottom:0;left:0;right:0;padding-top:12px;padding-bottom:12px;}"
        val element = "let chatInput=document.createElement('style');chatInput.innerHTML='$chatArea $chatInput';"
        val javascript = "javascript:${element}document.getElementsByTagName('head')[0].appendChild(chatInput);"
        dataBinding.videoView.loadUrl(javascript)
    }
}