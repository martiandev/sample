package team.standalone.core.data.util.fake

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.database.datasource.user.UserLocalDataSource
import team.standalone.core.database.room.model.entity.UserEntity
import javax.inject.Inject

class FakeUserLocalDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val fakeUserDao: FakeUserDao
) : UserLocalDataSource {

    override suspend fun saveUser(
        entity: UserEntity
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        try {
            fakeUserDao.insertOrReplace(entity)
        } catch (e: Exception) {
            Result.Error(e)
        }
        Result.Success(Unit)
    }

    override suspend fun getUser(uid: String): Result<UserEntity> {
        TODO("Not yet implemented")
    }

    override fun getUserAsFlow(uid: String): Flow<UserEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(uid: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Unit> {
        TODO("Not yet implemented")
    }
}