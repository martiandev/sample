package team.standalone.fumiya.ui.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.onimur.handlepathoz.HandlePathOz
import br.com.onimur.handlepathoz.HandlePathOzListener
import br.com.onimur.handlepathoz.model.PathOz
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.qualifier.Chat
import team.standalone.core.common.qualifier.Platform
import team.standalone.core.common.qualifier.UserAgent
import team.standalone.core.common.util.*
import team.standalone.core.data.domain.model.User
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.billing.BillingSubscriptionActivity
import team.standalone.fumiya.R
import team.standalone.fumiya.databinding.FragmentChatBinding
import team.standalone.fumiya.service.OneSignalService
import team.standalone.fumiya.ui.MainActivity
import team.standalone.fumiya.ui.MainViewModel
import team.standalone.fumiya.ui.auth.signin.AuthActivity
import team.standalone.fumiya.ui.base.ChatUiEvent.*
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
@DelicateCoroutinesApi
class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val chatViewModel: ChatViewModel by viewModels()

    @Inject
    @Platform
    lateinit var platform: String

    @Inject
    @UserAgent
    lateinit var userAgent: String

    @Inject
    @Chat
    lateinit var chatUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = FragmentChatBinding.inflate(inflater)
        )
        dataBinding.apply {
            fragment = this@ChatFragment
            viewModel = chatViewModel
        }
        return dataBinding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.chatView.apply {
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
                            ChatFragmentDirections
                                .actionNavigationChatToNavigationErrorNavigation()
                                .navigate(findNavController())
                        }
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    chatViewModel.setProgress(View.GONE)
                    view?.let { softVisible(it) }
                }
            }
            webChromeClient = CustomWebChromeClient()
            addJavascriptInterface(chatViewModel, platform)
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelStore.clear()
        chatViewModel.setProgress(View.VISIBLE)
    }

    /**
     * Method for handling flow lifecycle data collection.
     * */
    override fun collectData() {
        when (mainViewModel.token.isNotEmpty()) {
            true -> displayWebView(mainViewModel.token)
            else -> collectLatestLifecycleFlow(chatViewModel.idTokenResult) { result ->
                when(result) {
                    is LoadResult.Loading -> chatViewModel.setProgress(View.VISIBLE)
                    is LoadResult.Success -> displayWebView(result.data)
                    is LoadResult.Error -> navigateToAuthActivity()
                }
            }
        }
        collectLatestLifecycleFlow(chatViewModel.uiState) { uiState ->
            when(uiState.userState) {
                is LoadResult.Loading -> { }
                is LoadResult.Success, is LoadResult.Error -> {
                    checkIfAllowAccess(uiState.userState.data)
                }
            }
        }
        collectLatestLifecycleFlow(chatViewModel.uiEvent) { uiEvent ->
            when (uiEvent) {
                CameraJavascriptInterface -> selectPhoto()
                GalleryJavascriptInterface -> selectGallery()
                VideoJavascriptInterface -> selectVideo()
                is DataFileName -> sendVideoAppCallback(uiEvent.name)
            }
        }
        collectLatestLifecycleFlow(chatViewModel.imageResult) { result ->
            result.data?.let { sendImageAppCallback(it) }
        }
        collectLatestLifecycleFlow(chatViewModel.chatUploadResult) { result ->
            when(result) {
                is LoadResult.Loading -> { }
                is LoadResult.Success -> dismissLoadingDialog()
                is LoadResult.Error -> showUploadFailedMessage()
            }
        }
    }

    /**
     * Call 'appCallback.acceptPicture' javascript function to upload photo.
     * */
    private fun sendImageAppCallback(photo: String?) {
        val data = "\$nuxt.\$appCallback.acceptPicture('$photo')"
        dataBinding.chatView.evaluateJavascript(data) { }
    }

    /**
     * Call 'appCallback.acceptVideoFileName' javascript function to upload video file name.
     * */
    private fun sendVideoAppCallback(name: String) {
        val data = "\$nuxt.\$appCallback.acceptVideoFileName('$name')"
        dataBinding.chatView.evaluateJavascript(data) { }
    }

    /**
     * Set web view URL with id token and language.
     * */
    private fun displayWebView(idToken: String?) {
        when (idToken.isNullOrBlank() && isUrlEmpty()) {
            true -> navigateToAuthActivity()
            else -> collectLatestLifecycleFlow(chatViewModel.uiSettingsState) { uiState ->
                if(isUrlEmpty()) {
                    val language = chatViewModel.getLanguage(uiState.data)
                    val intentExtra = getIntentExtra()
                    val locale = chatViewModel.getLocaleQueryParam()
                    val url = "$chatUrl${intentExtra}idToken=$idToken$locale&language=$language"
                    dataBinding.chatView.loadUrl(url)
                }
            }
        }
    }

    /**
     * Check if url is empty or null.
     * */
    private fun isUrlEmpty() = dataBinding.chatView.url.isNullOrBlank()

    /**
     * Get extra values for specific chat navigation.
     * */
    private fun getIntentExtra(): String {
        activity?.intent?.extras?.let {
            val chatId = it.get(OneSignalService.CHAT_ID)
            val groupId = it.get(OneSignalService.GROUP_ID)
            if(chatId != null && groupId != null) {
                activity?.intent?.removeExtra(OneSignalService.CHAT_ID)
                activity?.intent?.removeExtra(OneSignalService.GROUP_ID)
                return "chatGroup/?chatId=$chatId&groupId=$groupId&"
            }
        }
        return "?"
    }

    /**
     * Opens the intent for choosing an image file found on the mobile device.
     * */
    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        resultPhoto.launch(intent)
    }

    /**
     * Opens the intent for choosing images or video files.
     * */
    private fun selectGallery() {
        if(activity?.checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
            }
            val mimeTypes = arrayOf("video/*", "image/*")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            resultGallery.launch(intent)
        } else {
            galleryRequestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    /**
     * Opens the intent for choosing an video file found on the mobile device.
     * */
    private fun selectVideo() {
        if(activity?.checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "video/*"
            }
            resultVideo.launch(intent)
        } else {
            videoRequestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    /**
     * Convert uri to file.
     * */
    @OptIn(FlowPreview::class)
    private fun uriToFile(uri: Uri, isChat: Boolean = true) {
        showUploadDialog()
        HandlePathOz(requireActivity(), object: HandlePathOzListener.SingleUri {
            override fun onRequestHandlePathOz(pathOz: PathOz, tr: Throwable?) {
                when (tr == null)  {
                    true -> handleFileAction(uri, File(pathOz.path), isChat)
                    false -> showUploadFailedMessage()
                }
            }
        }).getRealPath(uri)
    }

    /**
     * Sends the file for chat or bulletin upload.
     * */
    private fun handleFileAction(uri: Uri, file: File, isChat: Boolean = true) {
        if(isChat) {
            val images = arrayOf("jpg", "jpeg", "png", "gif")
            when(images.any { file.name.lowercase().contains(it) }) {
                true -> {
                    dismissLoadingDialog()
                    chatViewModel.formatPhoto(uri)
                }
                false -> chatViewModel.uploadChatVideo(file)
            }
        } else {
            chatViewModel.formatVideo(file)
        }
    }

    /**
     * Show upload failed image.
     * */
    private fun showUploadFailedMessage() {
        dismissLoadingDialog()
        MessageUtil.toast(
            requireContext(),
            R.string.fragment_chat_failed_upload
        )
    }

    /**
     * Show upload dialog and closes any web view dialog.
     * */
    private fun showUploadDialog() {
        val javascript = "javascript:document.getElementsByClassName('offButton')[0].click()"
        dataBinding.chatView.loadUrl(javascript)
        showLoadingDialog()
    }

    /**
     * activity result after selecting photos.
     * */
    private val resultPhoto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            intent?.data?.let { chatViewModel.formatPhoto(it) }
        }
    }

    /**
     * activity result after selecting images or video files.
     * */
    private val resultGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            intent?.data?.let { uriToFile(it) }
        }
    }

    /**
     * activity result after selecting video files.
     * */
    private val resultVideo = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            intent?.data?.let { uriToFile(it, isChat = false) }
        }
    }

    /**
     * Check if READ_EXTERNAL_STORAGE permission is approved for gallery.
     * */
    private val galleryRequestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if(it) selectGallery() }

    /**
     * Check if READ_EXTERNAL_STORAGE permission is approved for video.
     * */
    private val videoRequestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if(it) selectVideo() }

    /**
     * Navigate user back to auth screen.
     * */
    private fun navigateToAuthActivity() {
        chatViewModel.navigateToActivity(
            fragment = this,
            Intent(context, AuthActivity::class.java)
        )
    }

    /**
     * Get if user is allowed to access base screen.
     * If not, will redirect to MemberSubscriptionActivity or AuthActivity.
     * */
    private fun checkIfAllowAccess(user: User?) {
        if(user == null || !user.subscription.subscribed) {
            val navigate = when (user == null) {
                true -> AuthActivity::class.java
                false -> BillingSubscriptionActivity::class.java
            }
            chatViewModel.navigateToActivity(
                fragment = this,
                Intent(context, navigate)
            )
            (requireActivity() as? MainActivity)?.navBarSelect(R.id.navigation_home)
        }
    }
}