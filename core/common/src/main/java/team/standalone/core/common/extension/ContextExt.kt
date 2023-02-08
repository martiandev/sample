package team.standalone.core.common.extension

import android.app.ActivityManager
import android.app.Service
import android.content.Context

/**
 * Convert dp to pixel
 */
fun Context.convertDpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

/**
 * Convert pixel to dp
 */
fun Context.convertPxToDp(px: Int): Float = px / resources.displayMetrics.density

fun Context.clearAppData() {
    val am = getSystemService(Service.ACTIVITY_SERVICE) as? ActivityManager
    am?.clearApplicationUserData()
}