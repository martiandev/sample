package team.standalone.core.common.util

import kotlinx.coroutines.flow.*

sealed class LoadResult<out T>(val data: T? = null) {
    class Loading<out T>(data: T? = null) : LoadResult<T>(data)
    class Success<out T>(data: T? = null) : LoadResult<T>(data)
    class Error<out T>(data: T? = null, val exception: Exception) : LoadResult<T>(data)
}

inline fun <Domain, Entity, Remote> networkBoundResult(
    crossinline query: () -> Flow<Entity>,
    crossinline shouldFetch: (Entity?) -> Boolean,
    crossinline fetch: suspend () -> Result<Remote>,
    crossinline onFetchSuccess: suspend (Remote) -> Unit,
    crossinline onFetchFailed: (Exception) -> Unit,
    crossinline entityToDomain: (Entity?) -> Domain?,
) = flow {
    emit(LoadResult.Loading())
    val data = query().firstOrNull()

    val flow = if (shouldFetch(data)) {
        emit(LoadResult.Loading(entityToDomain(data)))

        try {
            val remote = when (val remoteResult = fetch()) {
                is Result.Success -> remoteResult.data
                is Result.Error -> throw remoteResult.exception
            }
            onFetchSuccess(remote)
            query().map { LoadResult.Success(entityToDomain(it)) }
        } catch (exception: Exception) {
            onFetchFailed(exception)
            query().map { LoadResult.Error(entityToDomain(it), exception) }
        }
    } else {
        query().map { LoadResult.Success(entityToDomain(it)) }
    }

    emitAll(flow)
}.catch {
    val exception = Exception(it)
    LoadResult.Error(null, exception)
}