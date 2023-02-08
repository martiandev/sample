package team.standalone.core.data.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.sqrt

class ParseUriToBase64UseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(uri: Uri): Result<String> = taskWithResult(ioDispatcher) {
        try {
            val imageQuality = 100
            val imageSize = 2000000f
            val bitmap = getCapturedPhoto(uri)
            val byteArray = ByteArrayOutputStream()
            val bitmapResize = resizePhoto(
                bitmap,
                imageSize,
                Matrix())
            val photoByteArray = compressBitmap(
                bitmapResize,
                imageQuality,
                byteArray)
            val result = byteArrayToBase64(photoByteArray)
            Result.Success(result)
        } catch (error: Exception) {
            Result.Error(error)
        }
    }

    /**
     * Get Bitmap value based from uri.
     * */
    fun getCapturedPhoto(photoUrl: Uri): Bitmap {
        val source = ImageDecoder.createSource(context.contentResolver, photoUrl)
        return ImageDecoder.decodeBitmap(source)
    }

    /**
     * Resize Bitmap photo to a specific size.
     * */
    fun resizePhoto(photo: Bitmap, toSize: Float, matrix: Matrix): Bitmap {
        val filter = true
        val size = photo.byteCount
        val scale = sqrt((toSize / size.toFloat()).toDouble()).toFloat()
        matrix.postScale(scale, scale)
        return Bitmap.createBitmap(photo, 0, 0, photo.width, photo.height, matrix, filter)
    }

    /**
     * Compress Bitmap to specific quality.
     * */
    fun compressBitmap(photo: Bitmap, quality: Int, byteArray: ByteArrayOutputStream): ByteArray {
        photo.compress(Bitmap.CompressFormat.JPEG, quality, byteArray)
        return byteArray.toByteArray()
    }

    /**
     * Confirm ByteArray to Base64 string.
     * */
    fun byteArrayToBase64(photo: ByteArray): String {
        return Base64.encodeToString(photo, Base64.NO_WRAP)
    }
}