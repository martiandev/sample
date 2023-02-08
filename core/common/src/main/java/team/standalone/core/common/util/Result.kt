package team.standalone.core.common.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

inline fun <reified T> Result<T>.doIfError(callback: (exception: Exception) -> Unit) {
    if (this is Result.Error) {
        callback(exception)
    }
}

inline fun <reified T> Result<T>.doIfSuccess(callback: (data: T) -> Unit) {
    if (this is Result.Success) {
        callback(data)
    }
}

inline fun <T> Result<T>.withDefault(data: () -> T): Result.Success<T> {
    return when (this) {
        is Result.Success -> this
        is Result.Error -> Result.Success(data())
    }
}

suspend inline fun <T : Any> taskWithResult(
    dispatcher: CoroutineDispatcher,
    crossinline task: suspend () -> Result<T>,
): Result<T> {
    return withContext(dispatcher) {
        try {
            task()
        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }
}

suspend inline fun <T : Any> taskWithResult(
    dispatcher: CoroutineDispatcher,
    crossinline task: suspend () -> Result<T>,
    crossinline exception: suspend (Exception) -> Unit
): Result<T> {
    return withContext(dispatcher) {
        try {
            task()
        } catch (e: Exception) {
            Lumberjack.error(e)
            try {
                exception(e)
            } catch (e1: Exception) {
                Lumberjack.error(e1)
            }
            Result.Error(e)
        }
    }
}

fun <T> Result<T>.asLoadResult(): LoadResult<T> {
    return when (this) {
        is Result.Success -> LoadResult.Success(data)
        is Result.Error -> LoadResult.Error(null, exception)
    }
}