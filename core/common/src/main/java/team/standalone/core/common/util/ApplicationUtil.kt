package team.standalone.core.common.util

import android.content.pm.PackageManager
import timber.log.Timber

object ApplicationUtil {

    /**
     * Checks if an app is installed
     * @param packageName - package name of the app to be searched
     * @return true - app is installed in the device false - app is not installed
     */
    fun checkIfInstalled(
        packageManager: PackageManager,
        packageName:String
    ):Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}