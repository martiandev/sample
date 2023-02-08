package team.standalone.core.common.extension

fun Long?.orZero(): Long {
    return this ?: 0L
}