package team.standalone.core.database.room.util

import androidx.room.*

abstract class BaseRoomDao<T> {
    @Insert
    abstract suspend fun insert(entity: T): Long

    @Insert
    abstract suspend fun insert(vararg entityList: T): LongArray

    @Insert
    abstract suspend fun insert(entityList: List<T>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrReplace(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrReplace(vararg entityList: T): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrReplace(entityList: List<T>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(vararg entityList: T): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(entityList: List<T>): LongArray

    @Update
    abstract suspend fun update(entity: T): Int

    @Update
    abstract suspend fun update(vararg entityList: T): Int

    @Update
    abstract suspend fun update(entityList: List<T>): Int

    @Delete
    abstract suspend fun delete(entity: T): Int

    @Delete
    abstract suspend fun delete(vararg entityList: T): Int

    @Delete
    abstract suspend fun delete(entityList: List<T>): Int

    @Transaction
    open suspend fun upsert(entity: T) {
        val id = insertOrIgnore(entity)
        if (id == -1L) update(entity)
    }

    @Transaction
    open suspend fun upsert(vararg entityList: T) {
        for (entity in entityList) {
            val id = insertOrIgnore(entity)
            if (id == -1L) update(entity)
        }
    }

    @Transaction
    open suspend fun upsert(entityList: List<T>) {
        for (entity in entityList) {
            val id = insertOrIgnore(entity)
            if (id == -1L) update(entity)
        }
    }
}