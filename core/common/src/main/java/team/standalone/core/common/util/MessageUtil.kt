package team.standalone.core.common.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

object MessageUtil {
    /**
     * Shows a toast message
     */
    fun toast(context: Context, @StringRes textResId: Int, duration: Int = Toast.LENGTH_SHORT) {
        toast(
            context = context,
            text = context.getString(textResId),
            duration = duration
        )
    }

    /**
     * Shows a toast message
     */
    fun toast(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, duration).show()
    }

    /**
     * Shows a snackbar message
     */
    fun snackbar(view: View, @StringRes textResId: Int, duration: Int = Snackbar.LENGTH_LONG) {
        snackbar(
            view = view,
            text = view.context.getString(textResId),
            duration = duration
        )
    }

    /**
     * Shows a snackbar message
     */
    fun snackbar(view: View, text: String, duration: Int = Snackbar.LENGTH_LONG) {
        val snackbar = Snackbar.make(view, text, duration)
        snackbar.show()
    }

    /**
     * Shows a snackbar message
     */
    fun snackbar(
        view: View,
        @StringRes contentTextResId: Int,
        @StringRes actionTextResId: Int,
        duration: Int = Snackbar.LENGTH_LONG,
        action: (View) -> Unit = {}
    ) {
        snackbar(
            view = view,
            contentText = view.context.getString(contentTextResId),
            actionText = view.context.getString(actionTextResId),
            duration = duration,
            action = action
        )
    }

    /**
     * Shows a snackbar message
     */
    fun snackbar(
        view: View,
        contentText: String,
        actionText: String,
        duration: Int = Snackbar.LENGTH_LONG,
        action: (View) -> Unit = {}
    ) {
        val snackbar = Snackbar.make(view, contentText, duration)
        snackbar.setAction(actionText, action)
        snackbar.show()
    }
}