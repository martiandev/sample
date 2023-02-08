package team.standalone.feature.userinfo.ui.cropphoto

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.isseiaoki.simplecropview.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.extension.collectLatestLifecycleFlow
import team.standalone.core.common.extension.withDataBinding
import team.standalone.core.common.qualifier.AppFileProvider
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.common.util.MessageUtil
import team.standalone.core.common.util.StorageUtil
import team.standalone.core.ui.androidcomponent.BaseFragment
import team.standalone.feature.userinfo.databinding.UserInfoFragmentCropPhotoBinding
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class CropPhotoFragment : BaseFragment<UserInfoFragmentCropPhotoBinding>() {
    private val cropPhotoViewModel: CropPhotoViewModel by viewModels()
    private val args: CropPhotoFragmentArgs by navArgs()

    @Inject
    @AppFileProvider
    lateinit var fileProvider: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = withDataBinding(
            fragment = this,
            binding = UserInfoFragmentCropPhotoBinding.inflate(inflater)
        )
        dataBinding.fragment = this
        dataBinding.viewModel = cropPhotoViewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(args.filePath).into(dataBinding.cropImageView)
        dataBinding.cropImageView.setCropEnabled(true)
        dataBinding.cropImageView.setCropMode(CropImageView.CropMode.CIRCLE)
        dataBinding.cropImageView.setInitialFrameScale(.75f)
    }

    override fun collectData() {
        collectLatestLifecycleFlow(cropPhotoViewModel.updateUserPhotoResult) { result ->
            when (result) {
                is LoadResult.Loading -> showLoadingDialog()
                is LoadResult.Success -> {
                    dismissLoadingDialog()
                    MessageUtil.toast(
                        context = requireContext(),
                        textResId = team.standalone.core.ui.R.string.uc_update_user_photo_success
                    )
                    findNavController().popBackStack()
                }
                is LoadResult.Error -> {
                    dismissLoadingDialog()
                    val errorMessage = when (result.exception) {
                        is AuthException.NetworkException -> getString(team.standalone.core.ui.R.string.uc_network_error)
                        else -> getString(team.standalone.core.ui.R.string.uc_update_user_photo_failed)
                    }
                    MessageUtil.toast(
                        context = requireContext(),
                        text = errorMessage
                    )
                }
            }
        }
    }

    fun apply() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val uri = bitmapToUri(dataBinding.cropImageView.croppedBitmap)
                cropPhotoViewModel.updatePhoto(uri!!)
            } catch (e: Exception) {
                Lumberjack.error(e)
            }
        }
    }

    private suspend fun bitmapToUri(bitmap: Bitmap) : Uri? {
        return try {
            val file = StorageUtil.createSharedCacheFile(
                context = requireContext(),
                mimeType = StorageUtil.MimeType.PNG
            )
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            }
            FileProvider.getUriForFile(
                requireContext(),
                fileProvider,
                file!!
            )
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    }
}