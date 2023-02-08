package team.standalone.core.network.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import team.standalone.core.network.firebase.util.FirebaseProperty
import java.util.*

data class ArtistRemote(
    @JvmField @PropertyName(FirebaseProperty.UID) var uid: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ARTIST_ICON) val icon: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ARTIST_CHAT_COLOR) val chatColor: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ARTIST_NICKNAME) val nickname: String? = null,
    @ServerTimestamp @JvmField @PropertyName(FirebaseProperty.CREATED_AT) val createdAt: Date? = null,
    @ServerTimestamp @JvmField @PropertyName(FirebaseProperty.UPDATED_AT) val updatedAt: Date? = null
)