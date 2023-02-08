package team.standalone.core.common.extension

fun Int?.orZero(): Int {
    return this ?: 0
}