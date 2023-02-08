package team.standalone.feature.other.ui.termsofservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.other.databinding.OtherFragmentTermsOfServiceBinding

@AndroidEntryPoint
class TermsOfServiceFragment : BaseFragment<OtherFragmentTermsOfServiceBinding>() {
    private val termsOfServiceViewModel: TermsOfServiceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = OtherFragmentTermsOfServiceBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = termsOfServiceViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView(dataBinding.webView)
    }

    private fun initWebView(webView: WebView) {
        webView.webViewClient = WebViewClient()
        webView.settings.builtInZoomControls = true
        webView.loadUrl("file:///android_asset/kiyaku.html")
    }
}