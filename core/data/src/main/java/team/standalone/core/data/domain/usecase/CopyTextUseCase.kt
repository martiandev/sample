package team.standalone.core.data.domain.usecase

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CopyTextUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * Copy text value to device's clipboard.
     * */
    operator fun invoke(text: String) {
        val clipboard = "CLIPBOARD"
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(clipboard, text)
        clipboardManager.setPrimaryClip(clipData)
    }
}