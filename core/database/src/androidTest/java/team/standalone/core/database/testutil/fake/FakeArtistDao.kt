package team.standalone.core.database.testutil.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import team.standalone.core.database.testutil.ArtistTest
import team.standalone.core.database.room.dao.ArtistDao
import team.standalone.core.database.room.model.entity.ArtistEntity
import java.lang.Exception

class FakeArtistDao: ArtistDao() {

    /**
     * Generates test ArtistEntity
     */
    lateinit var artistTest: ArtistTest

    init {
        artistTest = ArtistTest()
    }

    /**
     * Fakes getting an ArtistEntity using a uid
     * @param uid - uid of the fake ArtistEntity
     * @return ArtistEntity
     * test - will return an ArtistEntity with test1 uuid
     * others will return a null value
     */
    override suspend fun get(uid: String): ArtistEntity? {
        if(uid.equals("test")){
            return artistTest.getTestArtist(1)
        } else {
            return null
        }
    }

    /**
     * Fakes getting an ArtistEntity using a uid
     * @param uid - uid of the fake ArtistEntity
     * @return Flow<ArtistEntity>
     * test - will return an ArtistEntity with test1 uuid
     * others will return a null value
     */
    override fun getAsFlow(uid: String): Flow<ArtistEntity?> {
        if(uid.equals("test")){
            return flow {
                emit(
                    artistTest.getTestArtist(1)
                )
            }
        } else {
            return flow{
                emit(null)
            }
        }
    }

    /**
     * Fakes inserting an ArtistEntity
     * @param entity - ArtistEntity to be inserted
     * test1 - will result to successful insert
     * others will throw an exception
     * @return Long
     */
    override suspend fun insertOrReplace(entity: ArtistEntity): Long {
        return when(entity.uid.equals("test1")){
            true -> 1
            false -> {
                throw Exception("Random exception")
            }
        }
    }

    /**
     * Not used by LocalDataSource
     */
    override suspend fun delete(uid: String): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun delete(entity: ArtistEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun delete(vararg entityList: ArtistEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun delete(entityList: List<ArtistEntity>): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun deleteAll(): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insert(entity: ArtistEntity): Long {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insert(vararg entityList: ArtistEntity): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insert(entityList: List<ArtistEntity>): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrReplace(vararg entityList: ArtistEntity): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrReplace(entityList: List<ArtistEntity>): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrIgnore(entity: ArtistEntity): Long {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrIgnore(vararg entityList: ArtistEntity): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun insertOrIgnore(entityList: List<ArtistEntity>): LongArray {
        TODO("Not used for this fake scenario")
    }
    override suspend fun update(entity: ArtistEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun update(vararg entityList: ArtistEntity): Int {
        TODO("Not used for this fake scenario")
    }
    override suspend fun update(entityList: List<ArtistEntity>): Int {
        TODO("Not used for this fake scenario")
    }


}