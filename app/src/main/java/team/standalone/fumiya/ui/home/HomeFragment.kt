package team.standalone.fumiya.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
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
import team.standalone.core.common.qualifier.*
import team.standalone.core.common.qualifier.socialmedia.Facebook
import team.standalone.core.common.qualifier.socialmedia.Instragram
import team.standalone.core.common.qualifier.socialmedia.Tiktok
import team.standalone.core.common.qualifier.socialmedia.Twitter
import team.standalone.core.common.util.*
import team.standalone.core.common.util.CustomWebViewClient.Companion.VIDEO_LINK
import team.standalone.core.common.util.CustomWebViewClient.Companion.VIDEO_TITLE
import team.standalone.core.common.util.CustomWebViewClient.Companion.VIDEO_URL
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.fumiya.BuildConfig
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentHomeBinding
import team.standalone.fumiya.ui.MainViewModel
import team.standalone.fumiya.ui.auth.signin.AuthActivity
import team.standalone.fumiya.ui.park.video.VideoActivity
import team.standalone.fumiya.ui.util.web.InAppWebViewActivity
import team.standalone.fumiya.ui.util.web.InAppWebViewActivity.Companion.URL_WEB
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@DelicateCoroutinesApi
class HomeFragment : BaseFragment<FragmentHomeBinding>(){

    private val mainViewModel: MainViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = dataBinding
    lateinit var homeInterfaceModule:HomeInterfaceModule
    private var goBack = false

    @Inject
    @UserAgent
    lateinit var userAgent: String

    @Inject
    @StandAloneLocal
    lateinit var standAloneLocal: String

    @Inject
    @Instragram
    lateinit var instagram: String

    @Inject
    @Tiktok
    lateinit var tiktok: String

    @Inject
    @Twitter
    lateinit var twitter: String

    @Inject
    @Youtube
    lateinit var youtube: String

    @Inject
    @YoutubeMobile
    lateinit var youtubeMobile: String

    @Inject
    @Facebook
    lateinit var facebook: String

    @Inject
    @Platform
    lateinit var platform: String

    @Inject
    @Home
    lateinit var homeUrl: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeDataBinding(inflater)
        binding.progressBar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupActivity()
        addJavaScriptCallBacks()
        setupMenuOptions()
        displayWebView(mainViewModel.token)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        enableBackArrow(false)
    }

    override fun onOptionsItemSelected(
        item: MenuItem
    ): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun collectData() {
        collectLatestLifecycleFlow(mainViewModel.idTokenResult) { result ->
            when (result) {
                is LoadResult.Loading -> dataBinding.progressBar.visibility = View.VISIBLE
                is LoadResult.Success -> { displayWebView(result.data) }
                is LoadResult.Error -> displayError()
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
            binding = FragmentHomeBinding.inflate(inflater)
        )
        dataBinding.apply {
            dataBinding.fragment = this@HomeFragment
            dataBinding.viewModel = homeViewModel
        }
        binding.homeView.clearCache(true)
    }


    /**
     * Setups initial state of the activity
     */
    private fun setupActivity(){
        Timber.v("LIVE-URL setup activity")

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(this@HomeFragment) {

            onBackStack()
        }
        if (!BuildConfig.DEBUG) {
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    /**
     * Executes backpress on the webview
     * @param enable - displays the back arrow when true and hides the app bar when false
     */
    private fun onBackStack() {
        if (goBack) {
            binding.homeView.goBack()
        } else {
            activity?.let {
                requireActivity().finish()
            }
        }
    }

    /**
     * Setups an interface to handle javascript callbacks
     */
    private fun addJavaScriptCallBacks(){
        homeInterfaceModule = HomeInterfaceModule(
            homeViewModel,
            requireContext(),
            binding.homeView
        )
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
                        HomeFragmentDirections.actionGlobalNavigationOther()
                            .navigate(findNavController())
                        true
                    }
                    else -> true
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    /**
     * Loads the initial url after the idToken of the user was retrieved
     */
    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    fun displayWebView(
        token: String?
    ){

        lifecycleScope.launch {
            binding.homeView.apply {
                setHomeViewSettings(this)
                webChromeClient = CustomWebChromeClient()
                addJavascriptInterface(
                    homeInterfaceModule,
                    JS_INTERFACE_NAME
                )
                loadUrl(when(token==null){
                    true -> addLocale(
                        homeUrl
                    )
                    false -> addLocale(
                        "${homeUrl}?idToken=$token"
                    )
                })
            }
            setUpWebViewClient()

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
        val locale = homeViewModel.getLocaleQueryParam()
        return url+locale
    }

    /**
     * Configure webview settings
     * @param webView - reference to homeWebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun setHomeViewSettings(
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
        if(binding!=null){
            binding.homeView.webViewClient = object : CustomWebViewClient () {
                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Timber.v("LOG-URL url : error ${request?.url.toString()}")
                    if(isReceivedError(error?.errorCode)) {
                        activity?.let {
                            HomeFragmentDirections
                                .actionNavigationHomeToNavigationErrorNavigation()
                                .navigate(findNavController())
                        }
                    }

                }

                override fun onPageFinished(
                    view: WebView?,
                    url: String?
                ) {
                    binding.progressBar.visibility = View.GONE
                    view?.let { softVisible(it) }
                    if(url.equals(ERROR)) {
                        return
                    }
                    Timber.v("LOG-URL url : ${url}")
                    if(this@HomeFragment.isVisible()){
                        setCanBackPress(url.let {
                            when(it){
                                null -> ""
                                else -> it
                            }
                        })
                    }
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    binding.progressBar.visibility = View.VISIBLE
                    Timber.v("LOG-URL url : override ${request?.url}")

                    if (request?.url?.scheme == "standalone") {
                        Timber.v("LOG-URL url : override Standalone scheme")
                        when (request.url.authority) {
                            LINK_WEB -> return handleLinkWeb(view,request)
                        }
                    }

                    var url = request?.url.toString().let {
                        when(it){
                            null -> ""
                            else -> it
                        }
                    }
                    if(url.startsWith(standAloneLocal)){
                        return handleLinkWeb(view,request)
                    }
                    return processUrl(url)
                }
            }
        }
    }

    /**
     * Retrieves the local URL when the URL starts with standalone
     * @param url
     * @return Correct url
     */
    private fun retrieveLocalUrl(
        url: String
    ):String{
        return when(url.startsWith(standAloneLocal)){
            true-> url.replace(standAloneLocal,"")
            false-> url
        }
    }

    /**
     * Sets the goBack field depending on the url
     * @param url - url of page displayed in webview
     */
    private fun setCanBackPress(
        url:String
    ){
        goBack = binding.homeView.canGoBack()
        goBack = if(url!=null){
            if(isInternalWebView(url)){
                    true
            } else if (isApp(url)){
                false
            } else  {
                !url.startsWith(homeUrl)
            }
        } else {
            false
        }
        enableBackArrow(goBack)
    }

    /**
     * Checks if url should be launched to an internal web view
     * @param url - URL to be displayed
     * @return true - url should be displayed in an internal webview,
     * @return false - url should be displayed in an external webview
     */
    private fun isInternalWebView(url: String):Boolean = url.endsWith(MORE_INFO)
            || url.endsWith(KAPE_TAYO_INFO)
            || url.contains(TAG_VIDEO)

    /**
     * Checks if the url redirects to Social media
     * @param url - URL to be displayed
     * @return true - URL points to a social media
     * @return false - URL does not point to a social media
     */
    private fun isApp(url: String):Boolean = url.startsWith(facebook)
            || url.startsWith(instagram)
            || url.startsWith(tiktok)
            || url.startsWith(twitter)
            || url.startsWith(youtube)
            || url.startsWith(youtubeMobile)

    /**
     * Displays the back arrow on the appbar
     * @param enable - displays the back arrow when true and hides the app bar when false
     */
    private fun enableBackArrow(
        enable: Boolean
    ) {
        activity?.let {
            (it as AppCompatActivity).supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(enable)
                setHomeAsUpIndicator(R.drawable.ic_menu_ico_arrow_left)
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
     * Processes url for shouldOverrideUrlLoading
     * @param url - url to be processed
     * @return true - override URL loading false - follow default flow
     */
    private fun processUrl(
        url:String
    ):Boolean{
        return if(url.startsWith(facebook)){
            openApp(IntentUtil.FACEBOOK,url)
        } else if(url.startsWith(instagram)){
            openApp(IntentUtil.INSTAGRAM,url)
        } else if (url.startsWith(tiktok)){
            var tiktok = findTiktok()
            when(tiktok){
                null -> checkAndLaunchExternalBrowser(url)
                else -> openApp(
                    tiktok,
                    url
                )
            }
        } else if (url.startsWith(twitter)) {
            openApp(IntentUtil.TWITTER,url)
        } else if( url.startsWith(youtube)
            ||url.startsWith(youtubeMobile)) {
            openApp(IntentUtil.YOUTUBE,url)
        } else if (url.contains(VIDEO_LINK)) {
            openVideo(url)
        } else {
           false
        }
    }

    /**
     * Launches video activity
     * @param url - url for video
     * @return true - will not open video url in fragment, instead open on activity
     */
    private fun openVideo(
        url: String
    ): Boolean {
        binding.progressBar.visibility = View.GONE
        startActivity(Intent(requireContext(), VideoActivity::class.java).apply {
            putExtra(VIDEO_URL, url)
            putExtra(VIDEO_TITLE, getString(R.string.title_map))
        })
        return true
    }

    /**
     * Launches an app
     * @param packageName - package name of the app
     * @param url - utl to social media account
     * @return true - when app is installed in device and false - when app was not found
     */
    private fun openApp(
        packageName: String,
        url: String
    ):Boolean{
        var isInstalled = ApplicationUtil.checkIfInstalled(
            requireActivity().packageManager,
            packageName
        )
        when(isInstalled){
            true -> {
                runApp(packageName,url)
                return true
            }
            false-> {
                checkAndLaunchExternalBrowser(url)
                return true
            }
        }
    }

    /**
     * Search for the matching tiktok packagename
     * @return null - means tiktok is not installed
     */
    private fun findTiktok():String?{

        var packageNames = listOf<String>(
            IntentUtil.TIKTOK_ASIA,
            IntentUtil.TIKTOK_LITE,
            IntentUtil.TIKTOK
        )

        packageNames.forEach(){ packageName ->
            if(ApplicationUtil.checkIfInstalled(
                    requireActivity().packageManager,
                    packageName
            )){
                return  packageName
            }
        }

        return null;
    }

    /**
     * Launches the application
     * @param packageName - package name of the app
     * @param url - additional data to browse to Fumiya's account
     */
    private fun runApp(
        packageName: String,
        url: String
    ){
        goBack = false
        enableBackArrow(goBack)

        var intent =getIntent(
            url,
            packageName
        )
        when(intent){
            null -> {
                //do nothing
            }
            else -> {
                binding.progressBar.visibility = View.GONE
                startActivity(intent)
            }
        }
    }

    /**
     * Generates intent to be launched
     * @param url - URL to be accessed
     * @param packageName - packagename of the app to be opened
     * @return intent - if the target app is installed
     * @return null - if the target app is not installed
     */
    fun getIntent(
        url:String,
        packageName: String
    ):Intent?{
        val intent = when(url.startsWith(facebook)){
            true -> IntentUtil.getFacebookIntent(url)
            false ->  IntentUtil.getAppIntent(packageName,url)
        }
        return intent
    }

    /**
     * Checks if an external web browser is available
     * @param url - link to the web page
     * @return true - if an external web browser is available false if not
     */
    private fun checkAndLaunchExternalBrowser(
        url: String
    ):Boolean{
       var intent = IntentUtil
           .getBrowserIntent(url)
        when(intent){
            null -> {
                return false
            }
            else -> {
                launchWeb(intent)
                return true
            }
        }
    }

    /**
     * Launch the extrnal browser
     * @param url - link to the web page
     */
    private fun launchWeb(
        intent: Intent
    ){
        goBack = false
        enableBackArrow(goBack)
        binding.progressBar.visibility = View.GONE
        startActivity(intent)
    }

    /**
     * Handle Web Links and process url
     * @param view - WebView that will display the url
     * @param request - request to open the url
     * @return true - WebPage was loaded
     * @return false - WebPage was not loaded
     */
    private fun handleLinkWeb(
        view: WebView?,
        request: WebResourceRequest?
    ):Boolean{
        view.let { webView->
            when(webView){
                null -> return false
                else -> request.let { webRequest->
                    when(webRequest){
                        null -> return false
                        else -> {
                            val url = webRequest.url.getQueryParameter("url")
                            Timber.v("LOG-URL url = $url")

                            url?.let {
                                var openUrl:String = ""
                                if(url.startsWith(standAloneLocal)){
                                    openUrl = retrieveLocalUrl(url)
                                } else {
                                    openUrl = webRequest.url.query?.substring(4) as String
                                }
                                binding.progressBar.visibility = View.GONE
                                startActivity(Intent(requireContext(), InAppWebViewActivity::class.java).apply {
                                    putExtra(URL_WEB, openUrl)
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                })
                                return true
                            }
                            return true
                        }
                    }
                }
            }
        }

    }

    companion object {

        //Suffix of url to access More Info
        private const val MORE_INFO = "primary"
        //Suffix of url to access Kape Tayo More Info
        private const val KAPE_TAYO_INFO = "secondary"
        //Name of interface for JavaScript callbacks
        private const val JS_INTERFACE_NAME = "Android"
        //Video tag
        private const val TAG_VIDEO = "video"
        //Link is a web link
        private const val LINK_WEB = "local.web"
    }
}