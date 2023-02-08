package team.standalone.core.data.data.mapper

import team.standalone.core.common.mapper.BaseMapper
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.network.model.request.AddressRequest
import team.standalone.core.network.model.request.BirthdateRequest
import team.standalone.core.network.model.request.UserRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserParamMapper @Inject constructor() : BaseMapper<UserParam, UserRequest> {

    override fun map(input: UserParam): UserRequest {
        return UserRequest(
            nickName = input.nickName,
            firstName = input.firstName,
            lastName = input.lastName,
            firstNameKana = input.firstNameKana,
            lastNameKana = input.lastNameKana,
            gender = when (input.gender) {
                User.Gender.MALE -> ConstantUtil.GENDER_LABEL_MALE
                User.Gender.FEMALE -> ConstantUtil.GENDER_LABEL_FEMALE
                User.Gender.NO_ANSWER -> ConstantUtil.GENDER_LABEL_UNANSWERED
                User.Gender.UNKNOWN -> ""
            },
            phoneNumber = input.phoneNumber,
            birthdate = input.birthdate.let { birthdateParam ->
                if (birthdateParam != null) {
                    BirthdateRequest(
                        day = birthdateParam.day,
                        month = birthdateParam.month,
                        year = birthdateParam.year
                    )
                } else {
                    BirthdateRequest(
                        day = ConstantUtil.BIRTHDATE_EMPTY,
                        month = ConstantUtil.BIRTHDATE_EMPTY,
                        year = ConstantUtil.BIRTHDATE_EMPTY
                    )
                }
            },
            address = input.address.let {
                AddressRequest(
                    number = it.number,
                    city = it.city,
                    prefecture = it.prefecture,
                    structure = it.structure,
                    postalCode = it.postalCode
                )
            }
        )
    }
}