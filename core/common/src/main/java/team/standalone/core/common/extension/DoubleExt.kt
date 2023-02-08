package team.standalone.core.common.extension

fun Double?.orZero(): Double {
    return this ?: 0.0
}