package team.standalone.fumiya.ui.park.photo

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import team.standalone.core.common.qualifier.Photo
import team.standalone.core.common.qualifier.UserAgent
import team.standalone.core.common.util.CustomWebChromeClient
import team.standalone.core.common.util.CustomWebViewClient
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.navigate
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.databinding.FragmentPhotoBinding
import team.standalone.fumiya.ui.MainViewModel
import team.standalone.fumiya.ui.auth.signin.AuthActivity
import team.standalone.fumiya.ui.park.tab.ParkFragmentDirections
import javax.inject.Inject

@AndroidEntryPoint
@DelicateCoroutinesApi
class PhotoFragment: BaseFragment<FragmentPhotoBinding>() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val photoViewModel: PhotoViewModel by viewModels()

    @Inject
    @UserAgent
    lateinit var userAgent: String

    @Inject
    @Photo
    lateinit var photoUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentPhotoBinding.inflate(inflater)
        )
        dataBinding.apply {
            fragment = this@PhotoFragment
            viewModel = photoViewModel
        }
        return dataBinding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.photoView.apply {
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

                override fun onPageFinished(view: WebView?, url: String?) {
                    photoViewModel.setProgress(View.GONE)
                    view?.let { softVisible(it) }
                }
            }
            webChromeClient = CustomWebChromeClient()
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        photoViewModel.setProgress(View.VISIBLE)
    }

    override fun collectData() {
        when (mainViewModel.token.isNotEmpty()) {
            true -> displayWebView(mainViewModel.token)
            else -> collectLatestLifecycleFlow(mainViewModel.idTokenResult) { result ->
                when(result) {
                    is LoadResult.Loading -> photoViewModel.setProgress(View.VISIBLE)
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
        if(dataBinding.photoView.url.isNullOrBlank()) {
            idToken?.let {
                val locale = photoViewModel.getLocaleQueryParam()
                val url = "$photoUrl?idToken=$idToken$locale"
                dataBinding.photoView.loadUrl(url)
                return
            }
            navigateToAuthActivity()
        }
    }

    /**
     * Navigate user back to auth screen.
     * */
    private fun navigateToAuthActivity() {
        photoViewModel.navigateToActivity(
            fragment = this,
            Intent(context, AuthActivity::class.java)
        )
    }
}