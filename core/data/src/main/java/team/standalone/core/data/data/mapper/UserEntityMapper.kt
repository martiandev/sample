package team.standalone.core.data.data.mapper

import team.standalone.core.common.mapper.EntityMapper
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.data.domain.model.Address
import team.standalone.core.data.domain.model.Birthdate
import team.standalone.core.data.domain.model.Subscription
import team.standalone.core.data.domain.model.User
import team.standalone.core.database.room.model.entity.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserEntityMapper @Inject constructor() : EntityMapper<UserEntity, User> {

    override fun mapToDomain(entity: UserEntity): User {
        return User(
            uid = entity.uid,
            email = entity.email,
            photoUrl = entity.photoUrl,
            nickname = entity.nickname,
            firstName = entity.firstName,
            lastName = entity.lastName,
            firstNameKana = entity.firstNameKana,
            lastNameKana = entity.lastNameKana,
            memberNumber = entity.memberNumber,
            phoneNumber = entity.phoneNumber,
            gender = when (entity.gender) {
                ConstantUtil.GENDER_LABEL_FEMALE -> User.Gender.FEMALE
                ConstantUtil.GENDER_LABEL_MALE -> User.Gender.MALE
                ConstantUtil.GENDER_LABEL_UNANSWERED -> User.Gender.NO_ANSWER
                else -> User.Gender.UNKNOWN
            },
            withdraw = entity.withdraw,
            birthdate = entity.birthdate.let {
                Birthdate(
                    day = it.day,
                    month = it.month,
                    year = it.year
                )
            },
            address = entity.address.let {
                Address(
                    structure = it.structure,
                    number = it.number,
                    city = it.city,
                    prefecture = it.prefecture,
                    postalCode = it.postalCode
                )
            },
            subscription = entity.subscription.let {
                Subscription(
                    subscribed = it.subscribed,
                    paused = it.paused,
                    firstBillingDate = it.firstBillingDate,
                    startDate = it.startDate,
                    expireDate = it.expireDate
                )
            },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    override fun mapToDomain(entityList: List<UserEntity>): List<User> {
        return entityList.map { mapToDomain(it) }
    }
}