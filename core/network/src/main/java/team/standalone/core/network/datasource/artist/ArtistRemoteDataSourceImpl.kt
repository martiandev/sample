package team.standalone.core.network.datasource.artist

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import team.standalone.core.common.exception.ArtistException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.datasource.user.UserRemoteDataSource
import team.standalone.core.network.firebase.util.ArtistCollection
import team.standalone.core.network.firebase.util.FirebaseProperty
import team.standalone.core.network.firebase.util.toRemoteModel
import team.standalone.core.network.model.ArtistRemote
import javax.inject.Inject

internal class ArtistRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRemoteDataSource: UserRemoteDataSource,
    @ArtistCollection private val artistCollection: CollectionReference
) : ArtistRemoteDataSource {

    override suspend fun getArtist(): Result<ArtistRemote> = taskWithResult(ioDispatcher) {
        val id = when (val res = userRemoteDataSource.getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        val document = artistCollection.document(id).get().await()
        val artistRemote = document?.toRemoteModel<ArtistRemote>()?.let {
            if (it.uid.isNullOrBlank()) {
                it.uid = id
            }
            it
        } ?: throw ArtistException.ArtistNotFoundException

        Result.Success(artistRemote)
    }

    override suspend fun updateArtistPhoto(
        photo: String
    ): Result<ArtistRemote> = taskWithResult(ioDispatcher) {
        val uid = when (val res = userRemoteDataSource.getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val requestMap = hashMapOf(
            FirebaseProperty.ICON to "${ConstantUtil.IMAGE_PREFIX}$photo",
        )

        artistCollection.document(uid)
            .set(requestMap, SetOptions.merge())
            .await()

        val artistRemote = when (val res = getArtist()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(artistRemote)
    }

    override suspend fun deleteArtistPhoto(): Result<ArtistRemote> = taskWithResult(ioDispatcher) {
        val uid = when (val res = userRemoteDataSource.getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val updates = hashMapOf<String, Any>(
            FirebaseProperty.ICON to FieldValue.delete(),
        )

        artistCollection.document(uid)
            .update(updates)
            .await()

        val artistRemote = when (val res = getArtist()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(artistRemote)
    }
}