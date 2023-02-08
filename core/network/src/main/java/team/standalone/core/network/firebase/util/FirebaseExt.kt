package team.standalone.core.network.firebase.util

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.datetime.Instant
import team.standalone.core.common.util.Lumberjack

internal inline fun <reified T> DocumentSnapshot.toRemoteModel(): T? {
    return if (this.exists()) {
        try {
            Lumberjack.debug("Document data: $data")
            this.toObject<T>()
        } catch (e: Exception) {
            Lumberjack.error(e)
            null
        }
    } else {
        Lumberjack.error("Document does not exist")
        null
    }
}

internal fun Timestamp.toInstant(): Instant {
    val milliseconds = this.seconds * 1000 + this.nanoseconds / 1000000
    return Instant.fromEpochMilliseconds(milliseconds)
}