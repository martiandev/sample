package team.standalone.core.data.util.fake

import kotlinx.coroutines.flow.Flow
import team.standalone.core.database.room.dao.UserDao
import team.standalone.core.database.room.model.entity.UserEntity

class FakeUserDao : UserDao() {

    override suspend fun insertOrReplace(entity: UserEntity): Long {
        return when (entity.uid == "test-uid") {
            true -> 1
            false -> {
                throw Exception("Random exception")
            }
        }
    }

    override suspend fun get(uid: String): UserEntity? {
        TODO("Not yet implemented")
    }

    override fun getAsFlow(uid: String): Flow<UserEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(uid: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: UserEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg entityList: UserEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entityList: List<UserEntity>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun insert(entity: UserEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insert(vararg entityList: UserEntity): LongArray {
        TODO("Not yet implemented")
    }

    override suspend fun insert(entityList: List<UserEntity>): LongArray {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplace(vararg entityList: UserEntity): LongArray {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrReplace(entityList: List<UserEntity>): LongArray {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrIgnore(entity: UserEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrIgnore(vararg entityList: UserEntity): LongArray {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrIgnore(entityList: List<UserEntity>): LongArray {
        TODO("Not yet implemented")
    }

    override suspend fun update(entity: UserEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun update(vararg entityList: UserEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun update(entityList: List<UserEntity>): Int {
        TODO("Not yet implemented")
    }
}