package team.standalone.fumiya.ui.park.video

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.qualifier.UserAgent
import team.standalone.core.common.qualifier.Video
import team.standalone.core.common.util.*
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.databinding.FragmentVideoBinding
import team.standalone.fumiya.ui.MainViewModel
import team.standalone.fumiya.ui.auth.signin.AuthActivity
import team.standalone.fumiya.ui.park.tab.ParkFragmentDirections
import javax.inject.Inject

@AndroidEntryPoint
@DelicateCoroutinesApi
class VideoFragment: BaseFragment<FragmentVideoBinding>() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val videoViewModel: VideoViewModel by viewModels()

    @Inject
    @UserAgent
    lateinit var userAgent: String

    @Inject
    @Video
    lateinit var videoUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentVideoBinding.inflate(inflater)
        )
        dataBinding.apply {
            fragment = this@VideoFragment
            viewModel = videoViewModel
        }
        return dataBinding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.videoView.apply {
            settings.apply {
                domStorageEnabled = true
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                userAgentString = "$userAgentString$userAgent"
            }
            webViewClient = object: CustomWebViewClient() {
                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    if(isReceivedError(error?.errorCode)) {
                        activity?.let {
                            ParkFragmentDirections
                                .actionNavigationParkToNavigationErrorNavigation()
                                .navigate(findNavController())
                        }
                    }
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    request?.url?.toString()?.let { url ->
                        when (url.contains(VIDEO_LINK)) {
                            true -> startActivity(Intent(requireContext(), VideoActivity::class.java).apply {
                                putExtra(VIDEO_URL, request.url.toString())
                            })
                            false -> activity?.startActivity(IntentUtil.getBrowserIntent(url))
                        }
                    }
                    return true
                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    videoViewModel.setProgress(View.GONE)
                    view?.let { softVisible(it) }
                }
            }
            webChromeClient = CustomWebChromeClient()
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoViewModel.setProgress(View.VISIBLE)
    }

    override fun collectData() {
        when (mainViewModel.token.isNotEmpty()) {
            true -> displayWebView(mainViewModel.token)
            else -> collectLatestLifecycleFlow(videoViewModel.idTokenResult) { result ->
                when(result) {
                    is LoadResult.Loading -> videoViewModel.setProgress(View.VISIBLE)
                    is LoadResult.Success -> displayWebView(result.data)
                    is LoadResult.Error -> navigateToAuthActivity()
                }
            }
        }
    }

    /**
     * Loads the url after getting the idToken from Firebase auth.
     */
    private fun displayWebView(idToken: String?) {
        if(dataBinding.videoView.url.isNullOrBlank()) {
            idToken?.let {
                val locale = videoViewModel.getLocaleQueryParam()
                val url = "$videoUrl?idToken=$idToken$locale"
                dataBinding.videoView.loadUrl(url)
                return
            }
            navigateToAuthActivity()
        }
    }

    /**
     * Navigate user back to auth screen.
     * */
    private fun navigateToAuthActivity() {
        videoViewModel.navigateToActivity(
            fragment = this,
            Intent(context, AuthActivity::class.java)
        )
    }
}