package team.standalone.feature.userinfo.ui.updateuser

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.extension.updateIfNull
import team.standalone.core.common.util.*
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.AddressParam
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.data.domain.usecase.GetUserUseCase
import team.standalone.core.data.domain.usecase.UpdateUserUseCase
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.R
import team.standalone.core.ui.util.PrefectureUtil.translatePrefectureToEnglish
import team.standalone.core.ui.util.PrefectureUtil.translatePrefectureToKanji
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import team.standalone.feature.userinfo.ui.updateuser.UpdateUserUiEvent.NavigateToSelectBirthdate
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    application: Application,
    getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseAndroidViewModel<UpdateUserUiEvent>(application), UpdateUserUiEventListener {
    val nickname = FormItemAsFlow<String, String>()
    val lastName = FormItemAsFlow<String, String>()
    val firstName = FormItemAsFlow<String, String>()
    val lastNameKana = FormItemAsFlow<String, String>()
    val firstNameKana = FormItemAsFlow<String, String>()
    val birthdate = FormItemAsFlow<BirthdateParam, BirthdateParam>()
    val birthdateYear = FormItemAsFlow<Int, Int>()
    val birthdateMonth = FormItemAsFlow<Int, Int>()
    val birthdateDay = FormItemAsFlow<Int, Int>()
    val gender = FormItemAsFlow<User.Gender, User.Gender>()
    val addressPostalCode = FormItemAsFlow<String, String>()
    val addressPrefecture = FormItemAsFlow<String, String>()
    val addressCity = FormItemAsFlow<String, String>()
    val addressNumber = FormItemAsFlow<String, String>()
    val addressStructure = FormItemAsFlow<String, String>()
    val phoneNumber = FormItemAsFlow<String, String>()

    val nameError = FormError()
    val nameKanaError = FormError()

    private val name = combine(
        firstName.input,
        lastName.input
    ) { firstName, lastName ->
        !firstName.isNullOrEmpty() && !lastName.isNullOrEmpty()
    }

    val enableUpdate = combine(
        nickname.input,
        name,
    ) { nickName, name ->
        !nickName.isNullOrEmpty() && name
    }

    val uiState = getUserUseCase(true)
        .map { result ->
            UpdateUserUiState(result)
        }.stateIn(
            scope = viewModelScope,
            started = whileSubscribed,
            initialValue = UpdateUserUiState(LoadResult.Loading())
        )

    private val _updateUserResult = Channel<LoadResult<User>>()
    val updateUserResult = _updateUserResult.receiveAsFlow()

    fun initForm(user: User) {
        gender.input.updateIfNull(user.gender)
        nickname.input.updateIfNull(user.nickname)
        firstName.input.updateIfNull(user.firstName)
        lastName.input.updateIfNull(user.lastName)
        firstNameKana.input.updateIfNull(user.firstNameKana)
        lastNameKana.input.updateIfNull(user.lastNameKana)
        updateBirthdate(
            user.birthdate.let {
                if (it.day == ConstantUtil.BIRTHDATE_EMPTY
                    || it.month == ConstantUtil.BIRTHDATE_EMPTY
                    || it.year == ConstantUtil.BIRTHDATE_EMPTY
                ) {
                    null
                } else {
                    BirthdateParam(day = it.day, month = it.month, year = it.year)
                }
            }
        )
        gender.input.updateIfNull(user.gender)
        addressPostalCode.input.updateIfNull(user.address.postalCode)
        addressPrefecture.input.updateIfNull(
            translatePrefectureToEnglish(
                context = getApplication(),
                prefecture = user.address.prefecture
            )
        )
        addressCity.input.updateIfNull(user.address.city)
        addressNumber.input.updateIfNull(user.address.number)
        addressStructure.input.updateIfNull(user.address.structure)
        phoneNumber.input.updateIfNull(user.phoneNumber)
    }

    fun updateBirthdate(birthdateParam: BirthdateParam?) {
        if (birthdateParam != null) {
            birthdate.input.update { birthdateParam }
            birthdateYear.input.update { birthdateParam.year }
            birthdateMonth.input.update { birthdateParam.month }
            birthdateDay.input.update { birthdateParam.day }
        } else {
            birthdate.input.update { null }
            birthdateYear.input.update { null }
            birthdateMonth.input.update { null }
            birthdateDay.input.update { null }
        }
    }

    override fun updateUser() {
        val nicknameInput = nickname.validate(
            validatorBlock = Validator::isNickNameValid,
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_update_user_failed_invalid_nickname_empty)
                } else {
                    getString(R.string.uc_update_user_failed_invalid_nickname)
                }
            }
        )

        val lastNameInput = lastName.validate(
            validatorBlock = Validator::isLastNameValid,
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_update_user_failed_invalid_last_name_empty)
                } else {
                    getString(R.string.uc_update_user_failed_invalid_last_name)
                }
            }
        )

        val firstNameInput = firstName.validate(
            validatorBlock = Validator::isFirstNameValid,
            outputBlock = { it },
            errorBlock = {
                if (it.isNullOrBlank()) {
                    getString(R.string.uc_update_user_failed_invalid_first_name_empty)
                } else {
                    getString(R.string.uc_update_user_failed_invalid_first_name)
                }
            }
        )

        val tempLastName = lastName.input.value
        val tempFirstName = firstName.input.value
        when {
            tempLastName.isNullOrBlank() && tempFirstName.isNullOrBlank() -> {
                getString(R.string.uc_update_user_failed_invalid_name_empty)
            }
            tempLastName.isNullOrBlank() -> {
                getString(R.string.uc_update_user_failed_invalid_last_name_empty)
            }
            tempFirstName.isNullOrBlank() -> {
                getString(R.string.uc_update_user_failed_invalid_first_name_empty)
            }
            else -> {
                if (!tempLastName.let(Validator::isLastNameValid)) {
                    getString(R.string.uc_update_user_failed_invalid_last_name)
                } else if (!tempFirstName.let(Validator::isFirstNameValid)) {
                    getString(R.string.uc_update_user_failed_invalid_first_name)
                } else {
                    null
                }
            }
        }.also { error ->
            error?.let { nameError.setError(it) } ?: nameError.clear()
        }

        val lastNameKanaInput = lastNameKana.validate(
            validatorBlock = Validator::isLastNameKanaValid,
            outputBlock = { it },
            optional = true
        )

        val firstNameKanaInput = firstNameKana.validate(
            validatorBlock = Validator::isFirstNameKanaValid,
            outputBlock = { it },
            optional = true
        )

        val birthdateInput = birthdate.validate(
            validatorBlock = { param ->
                param?.let {
                    Validator.isBirthdateValid(
                        day = it.day,
                        month = it.month,
                        year = it.year
                    )
                }.orFalse()
            },
            outputBlock = { it },
            errorBlock = {
                if (it != null) {
                    getString(R.string.uc_update_user_failed_invalid_birthdate)
                } else {
                    getString(R.string.uc_update_user_failed_invalid_birthdate_empty)
                }
            },
            optional = true
        )

        val genderInput = gender.input.value ?: User.Gender.FEMALE

        val addressPostalCodeInput = addressPostalCode.validate(
            validatorBlock = Validator::isAddressPostalCodeValid,
            outputBlock = { it },
            optional = true
        )
        val addressPrefectureInput = addressPrefecture.validate(
            validatorBlock = Validator::isAddressPrefectureValid,
            outputBlock = { it },
            optional = true
        )
        val addressCityInput = addressCity.validate(
            validatorBlock = Validator::isAddressCityValid,
            outputBlock = { it },
            optional = true
        )
        val addressNumberInput = addressNumber.validate(
            validatorBlock = Validator::isAddressNumberValid,
            outputBlock = { it },
            optional = true
        )
        val addressStructureInput = addressStructure.validate(
            validatorBlock = Validator::isAddressStructureValid,
            outputBlock = { it },
            optional = true
        )
        val phoneNumberInput = phoneNumber.validate(
            validatorBlock = Validator::isPhoneNumberValid,
            outputBlock = { it },
            optional = true
        )

        when {
            nicknameInput == null -> Unit
            firstNameInput == null -> Unit
            lastNameInput == null -> Unit
            firstNameKana.hasError.value -> Unit
            lastNameKana.hasError.value -> Unit
            birthdate.hasError.value -> Unit
            addressPostalCode.hasError.value -> Unit
            addressPrefecture.hasError.value -> Unit
            addressCity.hasError.value -> Unit
            addressNumber.hasError.value -> Unit
            addressStructure.hasError.value -> Unit
            phoneNumber.hasError.value -> Unit
            else -> {
                runUseCase {
                    val param = UserParam(
                        nickName = nicknameInput,
                        firstName = firstNameInput,
                        lastName = lastNameInput,
                        firstNameKana = firstNameKanaInput.orEmpty(),
                        lastNameKana = lastNameKanaInput.orEmpty(),
                        gender = genderInput,
                        phoneNumber = phoneNumberInput.orEmpty(),
                        birthdate = birthdateInput,
                        address = AddressParam(
                            number = addressNumberInput.orEmpty(),
                            city = addressCityInput.orEmpty(),
                            prefecture = translatePrefectureToKanji(
                                context = getApplication(),
                                prefecture = addressPrefectureInput.orEmpty()
                            ),
                            structure = addressStructureInput.orEmpty(),
                            postalCode = addressPostalCodeInput.orEmpty(),
                        )
                    )
                    _updateUserResult.send(LoadResult.Loading())
                    val result = updateUserUseCase(param)
                    _updateUserResult.send(result.asLoadResult())
                }
            }
        }
    }

    override fun openSelectBirthdate() {
        val uiEvent = NavigateToSelectBirthdate(birthdate.input.value)
        sendUiEvent(uiEvent)
    }
}

interface UpdateUserUiEventListener {
    fun updateUser()
    fun openSelectBirthdate()
}

sealed class UpdateUserUiEvent {
    data class NavigateToSelectBirthdate(val param: BirthdateParam?) : UpdateUserUiEvent()
}

data class UpdateUserUiState(
    val userState: LoadResult<User>
)