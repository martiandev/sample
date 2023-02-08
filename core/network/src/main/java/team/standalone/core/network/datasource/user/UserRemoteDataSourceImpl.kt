package team.standalone.core.network.datasource.user

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import team.standalone.core.common.exception.AuthException
import team.standalone.core.common.exception.UserException
import team.standalone.core.common.qualifier.IoDispatcher
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.common.util.Lumberjack
import team.standalone.core.common.util.Result
import team.standalone.core.common.util.taskWithResult
import team.standalone.core.network.firebase.util.FirebaseProperty
import team.standalone.core.network.firebase.util.UserCollection
import team.standalone.core.network.firebase.util.toRemoteModel
import team.standalone.core.network.model.UserRemote
import team.standalone.core.network.model.request.UserRequest
import team.standalone.core.network.util.ConnectionManager
import java.util.*
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val firebaseAuth: FirebaseAuth,
    @UserCollection private val userCollection: CollectionReference,
    private val connectionManager: ConnectionManager
) : UserRemoteDataSource {

    private fun getFirebaseUser(): Result<FirebaseUser> {
        return try {
            firebaseAuth.currentUser?.let { firebaseUser ->
                Result.Success(firebaseUser)
            } ?: throw AuthException.UnauthorizedException
        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }

    override fun getUserUid(): Result<String> {
        return try {
            val uid = when (val res = getFirebaseUser()) {
                is Result.Success -> res.data.uid
                is Result.Error -> return res
            }
            Result.Success(uid)
        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }

    override suspend fun getToken(): Result<String> {
        return try {
            val token = when (val res = getFirebaseUser()) {
                is Result.Success -> res.data.getIdToken(true)?.await()?.token.toString()
                is Result.Error -> return res
            }
            Result.Success(token)
        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }

    override fun getUserEmail(): Result<String> {
        return try {
            val email = when (val res = getFirebaseUser()) {
                is Result.Success -> res.data.email ?: throw UserException.EmailNotFoundException
                is Result.Error -> return res
            }
            Result.Success(email)
        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }

    override fun isEmailVerified(): Result<Unit> {
        return try {
            val isEmailVerified = when (val res = getFirebaseUser()) {
                is Result.Success -> res.data.isEmailVerified
                is Result.Error -> return res
            }

            if (isEmailVerified) {
                Result.Success(Unit)
            } else {
                throw AuthException.EmailNotVerifiedException
            }
        } catch (e: Exception) {
            Lumberjack.error(e)
            Result.Error(e)
        }
    }

    override suspend fun getUser(): Result<UserRemote> = taskWithResult(ioDispatcher) {
        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val email = when (val res = getUserEmail()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val document = userCollection.document(uid).get().await()
        val userRemote = document?.toRemoteModel<UserRemote>()?.let {
            if (it.uid.isNullOrBlank() && it.email.isNullOrBlank()) {
                it.copy(uid = uid, email = email)
            } else if (it.uid.isNullOrBlank()) {
                it.copy(uid = uid)
            } else if (it.email.isNullOrBlank()) {
                it.copy(email = email)
            } else {
                it
            }
        } ?: throw UserException.UserNotFoundException

        Result.Success(userRemote)
    }

    override suspend fun updateUser(
        request: UserRequest
    ): Result<UserRemote> = taskWithResult(ioDispatcher) {
        if (!connectionManager.isNetworkConnected()) throw AuthException.NetworkException

        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        val email = when (val res = getUserEmail()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val requestMap = request.toMap().apply {
            put(FirebaseProperty.UID, uid)
            put(FirebaseProperty.EMAIL, email)
            put(FirebaseProperty.UPDATED_AT, FieldValue.serverTimestamp())
        }

        userCollection.document(uid)
            .set(requestMap, SetOptions.merge())
            .await()

        val userRemote = when (val res = getUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(userRemote)
    }

    override suspend fun updateUserPhoto(
        photo: String
    ): Result<UserRemote> = taskWithResult(ioDispatcher) {
        if (!connectionManager.isNetworkConnected()) throw AuthException.NetworkException

        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val requestMap = hashMapOf(
            FirebaseProperty.ICON to "${ConstantUtil.IMAGE_PREFIX}$photo",
            FirebaseProperty.UPDATED_AT to FieldValue.serverTimestamp()
        )

        userCollection.document(uid)
            .set(requestMap, SetOptions.merge())
            .await()

        val userRemote = when (val res = getUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(userRemote)
    }

    override suspend fun deleteUserPhoto(): Result<UserRemote> = taskWithResult(ioDispatcher) {
        if (!connectionManager.isNetworkConnected()) throw AuthException.NetworkException

        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val updates = hashMapOf<String, Any>(
            FirebaseProperty.ICON to FieldValue.delete(),
            FirebaseProperty.UPDATED_AT to FieldValue.serverTimestamp()
        )

        userCollection.document(uid)
            .update(updates)
            .await()

        val userRemote = when (val res = getUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(userRemote)
    }

    override suspend fun updateEmail(
        newEmail: String
    ): Result<UserRemote> = taskWithResult(ioDispatcher) {

        firebaseAuth.setLanguageCode(Locale.getDefault().language)
        val firebaseUser = when (val res = getFirebaseUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        try {
            firebaseUser.updateEmail(newEmail).await()
        } catch (e: FirebaseAuthUserCollisionException) {
            throw AuthException.EmailExistedException
        } catch (e: FirebaseNetworkException) {
            throw AuthException.NetworkException
        } catch (e: Exception) {
            throw e
        }

        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        val email = when (val res = getUserEmail()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val requestMap = hashMapOf(
            FirebaseProperty.EMAIL to email,
            FirebaseProperty.UPDATED_AT to FieldValue.serverTimestamp(),
        )
        userCollection.document(uid)
            .set(requestMap, SetOptions.merge())
            .await()

        val userRemote = when (val res = getUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(userRemote)
    }

    override suspend fun updatePassword(
        newPassword: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {
        val firebaseUser = when (val res = getFirebaseUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        try {
            firebaseUser.updatePassword(newPassword).await()
        } catch (e: FirebaseNetworkException) {
            throw AuthException.NetworkException
        } catch (e: Exception) {
            throw e
        }

        Result.Success(Unit)
    }

    override suspend fun saveNewUser(
        request: UserRequest
    ): Result<UserRemote> = taskWithResult(ioDispatcher) {
        if (!connectionManager.isNetworkConnected()) throw AuthException.NetworkException

        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        val email = when (val res = getUserEmail()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val requestMap = request.toMap().apply {
            put(FirebaseProperty.UID, uid)
            put(FirebaseProperty.EMAIL, email)
            put(FirebaseProperty.CREATED_AT, FieldValue.serverTimestamp())
            put(FirebaseProperty.UPDATED_AT, FieldValue.serverTimestamp())
            put(FirebaseProperty.ROLE, "")
            put(FirebaseProperty.SUBSCRIPTION, false)
            put(FirebaseProperty.WITHDRAW, false)
            put(FirebaseProperty.SUBSCRIPTION_PAUSED, false)
            put(FirebaseProperty.FIRST_BILLING_DATE, "")
            put(FirebaseProperty.SUBSCRIPTION_START_DATE, "")
            put(FirebaseProperty.SUBSCRIPTION_EXPIRE_DATE, "")
            put(FirebaseProperty.PURCHASE_TOKEN_ANDROID, "")
            put(FirebaseProperty.MEMBERS_NUMBER, "")
            put(FirebaseProperty.PLAYER_ID, "")
            put(FirebaseProperty.PLATFORM, "")
        }

        userCollection.document(uid)
            .set(requestMap, SetOptions.merge())
            .await()

        val userRemote = when (val res = getUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }
        Result.Success(userRemote)
    }

    override suspend fun sendEmailVerification(
        verificationUrl: String
    ): Result<Unit> = taskWithResult(ioDispatcher) {

        firebaseAuth.setLanguageCode(Locale.getDefault().language)
        val firebaseUser = when (val res = getFirebaseUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setHandleCodeInApp(true)
            .setUrl(verificationUrl)
            .build()
        firebaseUser.sendEmailVerification(actionCodeSettings).await()
        Result.Success(Unit)
    }

    override suspend fun disableUser(): Result<Unit> = taskWithResult(ioDispatcher) {
        val uid = when (val res = getUserUid()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        userCollection.document(uid)
            .update(FirebaseProperty.WITHDRAW, true)
            .await()
        Result.Success(Unit)
    }

    override suspend fun deleteAccount(): Result<Unit> = taskWithResult(ioDispatcher) {
        val firebaseUser = when (val res = getFirebaseUser()) {
            is Result.Success -> res.data
            is Result.Error -> return@taskWithResult res
        }

        firebaseUser.delete().await()
        Result.Success(Unit)
    }
}