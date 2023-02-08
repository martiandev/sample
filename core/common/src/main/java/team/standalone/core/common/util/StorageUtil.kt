package team.standalone.core.common.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import org.apache.commons.io.FileUtils
import java.io.File

object StorageUtil {
    enum class MimeType {
        JPEG,
        PNG
    }

    private fun getExtension(mimeType: MimeType): String {
        return when (mimeType) {
            MimeType.JPEG -> "jpeg"
            MimeType.PNG -> "png"
        }
    }

    fun createFile(context: Context, mimeType: MimeType): File {
        return createFile(
            context = context,
            fileName = System.currentTimeMillis().toString(),
            mimeType = mimeType)
    }

    fun createFile(context: Context, fileName: String, mimeType: MimeType): File {
        return File(context.filesDir, "$fileName.${getExtension(mimeType)}")
    }

    fun createSharedFile(context: Context, mimeType: MimeType): File? {
        return createSharedFile(
            context = context,
            fileName = System.currentTimeMillis().toString(),
            mimeType = mimeType)
    }

    fun createSharedFile(context: Context, fileName: String, mimeType: MimeType): File? {
        return getSharedDir(context)?.let { dirFile ->
            File(dirFile, "$fileName.${getExtension(mimeType)}")
        }
    }

    private fun getSharedDir(context: Context): File? {
        val dir = File(context.filesDir, "shared")
        return if (dir.exists()) {
            dir
        } else {
            if (dir.mkdirs()) dir else null
        }
    }

    fun createCacheFile(context: Context, mimeType: MimeType): File {
        return createCacheFile(
            context = context,
            fileName = System.currentTimeMillis().toString(),
            mimeType = mimeType)
    }

    fun createCacheFile(context: Context, fileName: String, mimeType: MimeType): File {
        return File(context.cacheDir, "$fileName.${getExtension(mimeType)}")
    }

    fun createSharedCacheFile(context: Context, mimeType: MimeType): File? {
        return createSharedCacheFile(
            context = context,
            fileName = System.currentTimeMillis().toString(),
            mimeType = mimeType)
    }

    fun createSharedCacheFile(context: Context, fileName: String, mimeType: MimeType): File? {
        return getSharedCacheDir(context)?.let { dirFile ->
            File(dirFile, "$fileName.${getExtension(mimeType)}")
        }
    }

    private fun getSharedCacheDir(context: Context): File? {
        val dir = File(context.cacheDir, "shared")
        return if (dir.exists()) {
            dir
        } else {
            if (dir.mkdirs()) dir else null
        }
    }

    fun copy(context: Context, uri: Uri): File? {
        return try {
            context.contentResolver.getType(uri)
                ?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
                ?.let { File.createTempFile("temp_", ".${it}") }
                ?.also { file ->
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        FileUtils.copyInputStreamToFile(inputStream, file)
                    }
                }
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    }
}