package team.standalone.core.data.util.test

import kotlinx.datetime.Instant
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.database.room.model.entity.UserEntity
import team.standalone.core.database.room.model.vo.AddressVO
import team.standalone.core.database.room.model.vo.BirthdateVO
import team.standalone.core.database.room.model.vo.SubscriptionVO
import team.standalone.core.network.model.UserRemote
import java.util.*
import kotlin.collections.ArrayList

open class UserTestUtil {

    var expectedUid: String = "123abc"
    var expectedEmail: String = "test@fumiya.com"
    var expectedPhotoUrl: String = "data:/sample/jpg/base64.."
    var expectedNickname: String = "Admin"
    var expectedFirstName: String = "John"
    var expectedLastName: String = "Smith"
    var expectedFirstNameKana: String = "ジョン"
    var expectedLastNameKana: String = "スミス"
    var expectedMemberNumber: String = "123456"
    var expectedPhoneNumber: String = "01230112"
    var expectedGender = User.Gender.MALE
    var expectedWithdraw: Boolean = true
    var expectedDate: Date = Date()

    fun getTestUser(instant: Instant): User {
        return User(
            uid = expectedUid,
            email = expectedEmail,
            photoUrl = expectedPhotoUrl,
            nickname = expectedNickname,
            firstName = expectedFirstName,
            lastName = expectedLastName,
            firstNameKana = expectedFirstNameKana,
            lastNameKana = expectedLastNameKana,
            memberNumber = expectedMemberNumber,
            phoneNumber = expectedPhoneNumber,
            gender = expectedGender,
            withdraw = expectedWithdraw,
            birthdate = BirthdateTestUtil().getTestBirthdate(),
            address = AddressTestUtil().getTestAddress(),
            subscription = SubscriptionTestUtil().getTestSubscription(),
            createdAt = instant,
            updatedAt = instant
        )
    }

    fun getTestUserParam(): UserParam {
        return UserParam(
            nickName = expectedNickname,
            firstName = expectedFirstName,
            lastName = expectedLastName,
            firstNameKana = expectedFirstNameKana,
            lastNameKana = expectedLastNameKana,
            gender = expectedGender,
            phoneNumber = expectedPhoneNumber,
            birthdate = BirthdateTestUtil().getTestBirthdateParam(),
            address = AddressTestUtil().getTestAddressParam(),
        )
    }

    fun getTestUserEntity(instant: Instant): UserEntity {
        return UserEntity(
            uid = expectedUid,
            email = expectedEmail,
            photoUrl = expectedPhotoUrl,
            nickname = expectedNickname,
            firstName = expectedFirstName,
            lastName = expectedLastName,
            firstNameKana = expectedFirstNameKana,
            lastNameKana = expectedLastNameKana,
            memberNumber = expectedMemberNumber,
            phoneNumber = expectedPhoneNumber,
            gender = ConstantUtil.GENDER_LABEL_MALE,
            withdraw = expectedWithdraw,
            birthdate = BirthdateVO(
                day = 1,
                month = 1,
                year = 1995
            ),
            address = AddressVO(
                number = "",
                city = "",
                prefecture = "",
                structure = "",
                postalCode = "",
            ),
            subscription = SubscriptionVO(
                subscribed = true,
                paused = true,
                firstBillingDate = null,
                startDate = null,
                expireDate = null
            ),
            createdAt = instant,
            updatedAt = instant
        )
    }

    fun getTestUserEntityList(instant: Instant): MutableList<UserEntity> {
        val list: MutableList<UserEntity> = ArrayList()
        list.add(getTestUserEntity(instant))
        return list
    }

    fun getTestUserRemote(): UserRemote {
        return UserRemote(
            uid = expectedUid,
            email = expectedEmail,
            icon = expectedPhotoUrl,
            nickname = expectedNickname,
            firstName = expectedFirstName,
            lastName = expectedLastName,
            firstNameKana = expectedFirstNameKana,
            lastNameKana = expectedLastNameKana,
            memberNumber = expectedMemberNumber,
            phoneNumber = expectedPhoneNumber,
            gender = ConstantUtil.GENDER_LABEL_MALE,
            withdraw = expectedWithdraw,
            createdAt = expectedDate,
            updatedAt = expectedDate
        )
    }

    fun getTestUserRemoteList(): MutableList<UserRemote> {
        val list: MutableList<UserRemote> = java.util.ArrayList()
        list.add(getTestUserRemote())
        return list
    }
}