package team.standalone.core.common.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Size
import androidx.exifinterface.media.ExifInterface
import androidx.exifinterface.media.ExifInterface.ORIENTATION_NORMAL
import androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180
import androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270
import androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90
import androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

object PhotoUtils {

    private fun getFileRotation(file: File): Int? {
        return try {
            val exif = ExifInterface(file)
            val orientation = exif.getAttributeInt(
                TAG_ORIENTATION,
                ORIENTATION_NORMAL)
            when (orientation) {
                ORIENTATION_ROTATE_90 -> 90
                ORIENTATION_ROTATE_180 -> 180
                ORIENTATION_ROTATE_270 -> 270
                else -> null
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int,
    ): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun decodeBitmapFromFile(
        file: File,
        size: Size,
    ): Bitmap {
        val decodedBitmap = BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(file.path, this)
            inSampleSize = calculateInSampleSize(this, size.width, size.height)
            inJustDecodeBounds = false
            BitmapFactory.decodeFile(file.path, this)
        }

        return getFileRotation(file)?.let { rotation ->
            Bitmap.createBitmap(
                decodedBitmap,
                0,
                0,
                decodedBitmap.width,
                decodedBitmap.height,
                Matrix().apply { preRotate(rotation.toFloat()) },
                true)
        } ?: decodedBitmap
    }

    fun scaleFile(file: File, size: Size): File? {
        return try {
            val bitmap = decodeBitmapFromFile(file, size)
            File.createTempFile("temp_", ".${file.extension}")
                .also { tempFile ->
                    val format = when (tempFile.extension) {
                        "jpeg" -> Bitmap.CompressFormat.JPEG
                        "jpg" -> Bitmap.CompressFormat.JPEG
                        "png" -> Bitmap.CompressFormat.PNG
                        else -> Bitmap.CompressFormat.JPEG
                    }
                    FileOutputStream(tempFile).use { fos ->
                        bitmap.compress(format, 100, fos)
                    }
                }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    // TODO what to do here
    // fun bitmapToFile(
    //     bitmap: Bitmap,
    //     outputFile: File,
    //     format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    // ) {
    //     try {
    //         FileOutputStream(outputFile).use {
    //             bitmap.compress(format, 100, it)
    //         }
    //     } catch (e: Exception) {
    //         Timber.e(e)
    //     }
    // }
}