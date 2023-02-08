package team.standalone.core.database.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import team.standalone.core.database.room.model.vo.AddressVO
import team.standalone.core.database.room.model.vo.BirthdateVO
import team.standalone.core.database.room.model.vo.SubscriptionVO

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String?,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "first_name_kana") val firstNameKana: String,
    @ColumnInfo(name = "last_name_kana") val lastNameKana: String,
    @ColumnInfo(name = "member_number") val memberNumber: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "withdraw") val withdraw: Boolean,
    @Embedded(prefix = "birthdate_") val birthdate: BirthdateVO,
    @Embedded(prefix = "address_") val address: AddressVO,
    @Embedded(prefix = "subscription_") val subscription: SubscriptionVO,
    @ColumnInfo(name = "created_at") val createdAt: Instant?,
    @ColumnInfo(name = "updated_at") val updatedAt: Instant?,
)