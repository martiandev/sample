package team.standalone.core.common.extension

fun Float?.orZero(): Float {
    return this ?: 0f
}