package team.standalone.core.database.datasource.user

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import team.standalone.core.common.exception.UserException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.database.room.dao.UserDao
import team.standalone.core.database.room.model.entity.UserEntity
import javax.inject.Inject

internal class UserLocalDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userDao: UserDao,
) : UserLocalDataSource {

    override suspend fun getUser(
        uid: String
    ): Result<UserEntity> = taskWithResult(ioDispatcher) {
        userDao.get(uid)?.let {
            Result.Success(it)
        } ?: throw UserException.UserNotFoundException
    }

    override fun getUserAsFlow(
        uid: String
    ): Flow<UserEntity?> {
        return userDao.getAsFlow(uid)
            .distinctUntilChanged()
    }

    override suspend fun saveUser(
        entity: UserEntity
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        try {
            userDao.insertOrReplace(entity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }

    override suspend fun deleteUser(
        uid: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        try{
            userDao.delete(uid)
            Result.Success(Unit)
        }catch (e:Exception){
            Lumberjack.error(e)
            Result.Error(e)
        }

    }

    override suspend fun deleteAll(): Result<Unit> = taskWithResult(ioDispatcher) {
        try {
            userDao.deleteAll()
            Result.Success(Unit)
        }catch (e:Exception){
            e.printStackTrace()
            Result.Error(e)
        }
    }
}