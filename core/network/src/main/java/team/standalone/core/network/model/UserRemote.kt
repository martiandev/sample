package team.standalone.core.network.model

import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import team.standalone.core.network.firebase.util.FirebaseProperty
import java.util.*

data class UserRemote(
    @JvmField @PropertyName(FirebaseProperty.UID) val uid: String? = null,
    @JvmField @PropertyName(FirebaseProperty.EMAIL) val email: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ICON) val icon: String? = null,
    @JvmField @PropertyName(FirebaseProperty.NICKNAME) val nickname: String? = null,
    @JvmField @PropertyName(FirebaseProperty.FIRST_NAME) val firstName: String? = null,
    @JvmField @PropertyName(FirebaseProperty.LAST_NAME) val lastName: String? = null,
    @JvmField @PropertyName(FirebaseProperty.FIRST_NAME_KANA) val firstNameKana: String? = null,
    @JvmField @PropertyName(FirebaseProperty.LAST_NAME_KANA) val lastNameKana: String? = null,
    @JvmField @PropertyName(FirebaseProperty.MEMBERS_NUMBER) val memberNumber: String? = null,
    @JvmField @PropertyName(FirebaseProperty.PHONE_NUMBER) val phoneNumber: String? = null,
    @JvmField @PropertyName(FirebaseProperty.GENDER) val gender: String? = null,
    @JvmField @PropertyName(FirebaseProperty.WITHDRAW) val withdraw: Boolean? = null,
    @JvmField @PropertyName(FirebaseProperty.BIRTH_DAY) val birthDay: Int? = null,
    @JvmField @PropertyName(FirebaseProperty.BIRTH_MONTH) val birthMonth: Int? = null,
    @JvmField @PropertyName(FirebaseProperty.BIRTH_YEAR) val birthYear: Int? = null,
    @JvmField @PropertyName(FirebaseProperty.ADDRESS_STRUCTURE) val addressStructure: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ADDRESS_NUMBER) val addressNumber: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ADDRESS_CITY) val addressCity: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ADDRESS_PREFECTURE) val addressPrefecture: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ADDRESS_POSTAL_CODE) val addressPostalCode: String? = null,
    @JvmField @PropertyName(FirebaseProperty.SUBSCRIPTION) val subscription: Boolean? = null,
    @JvmField @PropertyName(FirebaseProperty.SUBSCRIPTION_PAUSED) val subscriptionPaused: Boolean? = null,
    @JvmField @PropertyName(FirebaseProperty.SUBSCRIPTION_START_DATE) val subscriptionStartDate: String? = null,
    @JvmField @PropertyName(FirebaseProperty.SUBSCRIPTION_EXPIRE_DATE) val subscriptionExpireDate: String? = null,
    @ServerTimestamp @JvmField @PropertyName(FirebaseProperty.CREATED_AT) val createdAt: Date? = null,
    @ServerTimestamp @JvmField @PropertyName(FirebaseProperty.UPDATED_AT) val updatedAt: Date? = null,

    // todo ask what these fields mean
    @JvmField @PropertyName(FirebaseProperty.FIRST_BILLING_DATE) val firstBillingDate: String? = null,
    @JvmField @PropertyName(FirebaseProperty.PLATFORM) val platform: String? = null,
    @JvmField @PropertyName(FirebaseProperty.PLAYER_ID) val playerId: String? = null,
    @JvmField @PropertyName(FirebaseProperty.PURCHASE_TOKEN_ANDROID) val purchaseTokenAndroid: String? = null,
    @JvmField @PropertyName(FirebaseProperty.ROLE) val role: String? = null,
)