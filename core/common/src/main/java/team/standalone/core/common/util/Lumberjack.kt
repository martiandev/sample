package team.standalone.core.common.util

import team.standalone.core.common.logging.DebugTree
import team.standalone.core.common.logging.ReleaseTree
import timber.log.Timber

object Lumberjack {

    fun plant(debug: Boolean) {
        if (debug) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    fun info(msg: String) {
        Timber.i(msg)
    }

    fun debug(msg: String) {
        Timber.d(msg)
    }

    fun warning(msg: String) {
        Timber.w(msg)
    }

    fun error(msg: String) {
        Timber.e(msg)
    }

    fun error(e: Exception) {
        Timber.e(e)
    }

    fun error(e: Throwable) {
        Timber.e(e)
    }
}