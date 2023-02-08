package team.standalone.core.data.data.mapper

import kotlinx.datetime.Instant
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.mapper.RemoteMapper
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.database.room.model.entity.UserEntity
import team.standalone.core.database.room.model.vo.AddressVO
import team.standalone.core.database.room.model.vo.BirthdateVO
import team.standalone.core.database.room.model.vo.SubscriptionVO
import team.standalone.core.network.model.UserRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRemoteMapper @Inject constructor() : RemoteMapper<UserRemote, UserEntity> {

    override fun mapToEntity(remote: UserRemote): UserEntity {
        return UserEntity(
            uid = remote.uid.orEmpty(),
            email = remote.email.orEmpty(),
            photoUrl = remote.icon.let { icon ->
                if (icon.isNullOrBlank()) null else icon.substringAfter(",")
            },
            nickname = remote.nickname.orEmpty(),
            firstName = remote.firstName.orEmpty(),
            lastName = remote.lastName.orEmpty(),
            firstNameKana = remote.firstNameKana.orEmpty(),
            lastNameKana = remote.lastNameKana.orEmpty(),
            memberNumber = remote.memberNumber.orEmpty(),
            phoneNumber = remote.phoneNumber.orEmpty(),
            gender = remote.gender.orEmpty(),
            withdraw = remote.withdraw.orFalse(),
            birthdate = BirthdateVO(
                day = remote.birthDay ?: ConstantUtil.BIRTHDATE_EMPTY,
                month = remote.birthMonth ?: ConstantUtil.BIRTHDATE_EMPTY,
                year = remote.birthYear ?: ConstantUtil.BIRTHDATE_EMPTY
            ),
            address = AddressVO(
                structure = remote.addressStructure.orEmpty(),
                number = remote.addressNumber.orEmpty(),
                city = remote.addressCity.orEmpty(),
                prefecture = remote.addressPrefecture.orEmpty(),
                postalCode = remote.addressPostalCode.orEmpty()
            ),
            subscription = SubscriptionVO(
                subscribed = remote.subscription.orFalse(),
                paused = remote.subscriptionPaused.orFalse(),
                firstBillingDate = remote.firstBillingDate?.toLongOrNull()
                    ?.let(Instant::fromEpochMilliseconds),
                startDate = remote.subscriptionStartDate?.toLongOrNull()
                    ?.let(Instant::fromEpochMilliseconds),
                expireDate = remote.subscriptionExpireDate?.toLongOrNull()
                    ?.let(Instant::fromEpochMilliseconds)
            ),
            createdAt = remote.createdAt?.time?.let(Instant::fromEpochMilliseconds),
            updatedAt = remote.updatedAt?.time?.let(Instant::fromEpochMilliseconds),
        )
    }

    override fun mapToEntity(remoteList: List<UserRemote>): List<UserEntity> {
        return remoteList.map { mapToEntity(it) }
    }
}