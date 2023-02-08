package team.standalone.core.network.util.test

import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.network.model.UserRemote
import team.standalone.core.network.model.request.UserRequest
import java.util.*

open class UserTestUtil {

    val expectedUid: String = "123abc"
    val expectedEmail: String = "test@fumiya.com"
    val expectedPhotoUrl: String = "data:/sample/jpg/base64.."
    val expectedNickname: String = "Admin"
    val expectedFirstName: String = "John"
    val expectedLastName: String = "Smith"
    val expectedFirstNameKana: String = "ジョン"
    val expectedLastNameKana: String = "スミス"
    val expectedMemberNumber: String = "123456"
    val expectedPhoneNumber: String = "01230112"
    val expectedGender = ConstantUtil.GENDER_LABEL_MALE
    val expectedWithdraw: Boolean = true

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
            gender = expectedGender,
            withdraw = expectedWithdraw,
            createdAt = Date(),
            updatedAt = Date()
        )
    }

    fun getTestUserRequest(): UserRequest {
        return UserRequest(
            nickName = expectedNickname,
            firstName = expectedFirstName,
            lastName = expectedLastName,
            firstNameKana = expectedFirstNameKana,
            lastNameKana = expectedLastNameKana,
            phoneNumber = expectedPhoneNumber,
            gender = expectedGender,
            birthdate = BirthdayTestUtil().getTestBirthdateRequest(),
            address = AddressTestUtil().getTestAddressRequest()
        )
    }
}