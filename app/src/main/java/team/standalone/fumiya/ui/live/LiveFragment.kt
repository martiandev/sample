package team.standalone.fumiya.ui.live

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.qualifier.LiveStreamWeb
import team.standalone.core.common.qualifier.Platform
import team.standalone.core.common.qualifier.UserAgent
import team.standalone.core.common.util.*
import team.standalone.core.data.domain.model.User
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.billing.BillingSubscriptionActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentLiveBinding
import team.standalone.fumiya.ui.MainActivity
import team.standalone.fumiya.ui.MainViewModel
import team.standalone.fumiya.ui.auth.signin.AuthActivity
import team.standalone.fumiya.ui.park.video.VideoActivity
import javax.inject.Inject

@AndroidEntryPoint
@DelicateCoroutinesApi
class LiveFragment: BaseFragment<FragmentLiveBinding>(){

    private val mainViewModel: MainViewModel by activityViewModels()
    private val liveViewModel: LiveViewModel by viewModels()

    @Inject
    @LiveStreamWeb
    lateinit var streamUrl:String

    @Inject
    @Platform
    lateinit var platform: String

    @Inject
    @UserAgent
    lateinit var userAgent: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeDataBinding(inflater)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenuOptions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liveViewModel.setProgress(View.VISIBLE)
        viewModelStore.clear()
    }

    override fun collectData() {
        collectLatestLifecycleFlow(
            liveViewModel.uiState
        ){ userState ->
            when(userState.userState){
                is LoadResult.Loading -> liveViewModel.setProgress(View.VISIBLE)
                is LoadResult.Success, is LoadResult.Error -> {
                    checkIfSubscribed(userState.userState.data)
                }
            }
        }

        when (mainViewModel.token.isNotEmpty()) {
            true -> displayWebView(mainViewModel.token)
            else -> collectLatestLifecycleFlow(
                mainViewModel.idTokenResult
            ) { result ->
                when (result) {
                    is LoadResult.Loading -> liveViewModel.setProgress(View.VISIBLE)
                    is LoadResult.Success -> displayWebView(result.data)
                    is LoadResult.Error -> displayError()
                }
            }
        }
    }

    /**
     * Checks if the User is subscribed
     * - launches AuthActivity if not authenticated
     * - launches BillingSubscriptionActivity if not subscribed
     * - retrieves ID Token if user is authenticated and subscribed
     * @param user - User to be verified
     */
    private fun checkIfSubscribed(user: User?){
        when(user){
            null -> launchActivity(AuthActivity::class.java)
            else -> {
                when(user.subscription.subscribed){
                    true -> liveViewModel.getIdTokenTask()
                    false -> launchActivity(BillingSubscriptionActivity::class.java)
                }
            }
        }
    }

    /**
     * Initializes data binding of the fragment and sets data variables in the xml
     */
    private fun initializeDataBinding(
        inflater: LayoutInflater
    ) {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentLiveBinding.inflate(inflater),
        )
        dataBinding.apply {
            viewModel = liveViewModel
        }
        dataBinding.liveView.clearCache(true)
    }

    /**
     * Loads the initial url after the idToken of the user was retrieved
     */
    private fun displayWebView(token: String?){
        lifecycleScope.launch {
            when (dataBinding.liveView.url.isNullOrBlank()) {
                true -> {
                    dataBinding.liveView.apply {
                        setLiveWebViewSettings(this)
                        webChromeClient = CustomWebChromeClient()
                        loadUrl(when(token.isNullOrBlank()){
                            true -> addLocale(
                                streamUrl
                            )
                            false -> addLocale(
                                "${streamUrl}?idToken=$token"
                            )
                        })
                    }
                    setUpWebViewClient()
                }
                else -> liveViewModel.setProgress(View.GONE)
            }
        }
    }

    /**
     * Appends locale to the URL
     * @param url - url to append the locale
     * @return new url with locale
     */
    private fun addLocale(
        url:String
    ):String {
        val locale = liveViewModel.getLocaleQueryParam()
        return url+locale
    }

    /**
     * Configure webview settings
     * @param webView - reference to liveView
     */
    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun setLiveWebViewSettings(
        webView: WebView
    ){
        webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportMultipleWindows(true)
                userAgentString = userAgent
                javaScriptCanOpenWindowsAutomatically = true
            }
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    /**
     * Set-up web client that handles callbacks from the home webview
     */
    private fun setUpWebViewClient(){
        dataBinding.liveView.webViewClient = object : CustomWebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if(isReceivedError(error?.errorCode)) {
                    activity?.let {
                        LiveFragmentDirections
                            .actionNavigationLiveToNavigationErrorNavigation()
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
                            putExtra(VIDEO_TITLE, getString(R.string.title_station))
                        })
                        false -> activity?.startActivity(IntentUtil.getBrowserIntent(url))
                    }
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                liveViewModel.setProgress(View.GONE)
                view?.let { softVisible(it) }
            }
        }
    }

    /**
     * Behavior when app encountered an error while retrieving idToken
     */
    private fun displayError(){
        startActivity(
            Intent(
                context,
                AuthActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    /**
     * Launches an Activity and returns to the home tab
     * @param activity - Activity to be launched
     */
    private fun launchActivity(
        activity:Class<*>
    ){
        startActivity(
            Intent(
                requireActivity(),
                activity
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
        (requireActivity() as? MainActivity)?.navBarSelect(R.id.navigation_home)
    }

    /**
     * Sets the app bar menu for user information
     */
    private fun setupMenuOptions() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_options_home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    //Opens Menu when menu is tapped
                    R.id.menu_options_other -> {
                        LiveFragmentDirections.actionGlobalNavigationOther()
                            .navigate(findNavController())
                        true
                    }
                    else -> true
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}