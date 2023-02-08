package team.standalone.core.common.extension

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.makeInvisibleIf(condition: () -> Boolean) {
    if (condition()) invisible() else visible()
}

fun View.makeGoneIf(condition: () -> Boolean) {
    if (condition()) gone() else visible()
}