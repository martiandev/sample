package team.standalone.core.database.testutil.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import team.standalone.core.database.testutil.UserTest
import team.standalone.core.database.room.dao.UserDao
import team.standalone.core.database.room.model.entity.UserEntity
import java.lang.Exception

class FakeUserDao: UserDao() {

    /**
     * Generates test UserEntity
     */
    lateinit var userTest: UserTest

    init {
        userTest = UserTest()
    }

    /**
     * Fakes getting a UserEntity using a uid
     * @param uid - uid of the fake UserEntity
     * @return UserEntity
     * test - will return an UserEntity with test1 uuid
     * others will return a null value
     */
    override suspend fun get(uid: String): UserEntity? {
        if(uid.equals("test")){
            return userTest.getTestUser(1)
        } else {
            return null
        }
    }

    /**
     * Fakes getting a UserEntity using a uid
     * @param uid - uid of the fake UserEntity
     * @return Flow<UserEntity>
     * test - will return an UserEntity with test1 uuid
     * others will return a null value
     */
    override fun getAsFlow(uid: String): Flow<UserEntity?> {
        if(uid.equals("test")){
            return flow {
                emit(
                    userTest.getUpdatedUser(1)
                )
            }
        } else {
            return flow{
                emit(null)
            }
        }
    }
    /**
     * Fakes inserting a UserEntity
     * @param entity - UserEntity to be inserted
     * test1 - will result to successful insert
     * others will throw an exception
     * @return Long
     */
    override suspend fun insertOrReplace(entity: UserEntity): Long {
        return when(entity.uid.equals("test1")){
            true -> 1
            false -> {
                throw Exception("Random exception")
            }
        }
    }
    /**
     * Fakes deleting a UserEntity
     * @param uid - uid of UserEntity to be deleted
     * test1 - will result to successful delete
     * others will throw an exception
     * @return Long
     */
    override suspend fun delete(uid: String): Int {
        if(uid.equals("test1")){
            return 1
        } else {
            throw Exception("Random exception")
        }
    }

    /**
     * Fakes deleting all UserEntity
     * @return Long
     */
    override suspend fun deleteAll(): Int {
        return 1
    }


    /**
     * Not used by LocalDataSource
     */
    override suspend fun delete(entity: UserEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun delete(vararg entityList: UserEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun delete(entityList: List<UserEntity>): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insert(entity: UserEntity): Long {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insert(vararg entityList: UserEntity): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insert(entityList: List<UserEntity>): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrReplace(vararg entityList: UserEntity): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrReplace(entityList: List<UserEntity>): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrIgnore(entity: UserEntity): Long {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrIgnore(vararg entityList: UserEntity): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrIgnore(entityList: List<UserEntity>): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun update(entity: UserEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun update(vararg entityList: UserEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun update(entityList: List<UserEntity>): Int {
        TODO("Not used for this fake scenario")
    }

}