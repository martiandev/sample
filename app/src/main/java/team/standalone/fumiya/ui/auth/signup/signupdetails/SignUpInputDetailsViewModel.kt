package team.standalone.fumiya.ui.auth.signup.signupdetails

import android.widget.CompoundButton
import androidx.databinding.ObservableInt
import dagger.hilt.android.lifecycle.HiltViewModel
import team.standalone.core.common.extension.orFalse
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.common.util.FormItem
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.AddressParam
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.data.domain.util.Validator
import team.standalone.core.ui.viewmodel.BaseViewModel
import team.standalone.fumiya.ui.auth.signup.signupdetails.SignUpInputDetailsUiEvent.*
import javax.inject.Inject

@HiltViewModel
class SignUpInputDetailsViewModel @Inject constructor(
) : BaseViewModel<SignUpInputDetailsUiEvent>(), SignUpInputDetailsUiEventListener {
    val nickName = FormItem<String, String>()
    val lastName = FormItem<String, String>()
    val firstName = FormItem<String, String>()
    val kanaLastName = FormItem<String, String>()
    val kanaFirstName = FormItem<String, String>()

    val birthdate = FormItem<BirthdateParam, BirthdateParam>()

    val gender = ObservableInt(ConstantUtil.GENDER_UNANSWERED)
    val postalCode = FormItem<String, String>()
    val addressPrefecture = FormItem<String, String>()
    val addressMunicipality = FormItem<String, String>()
    val addressComplete = FormItem<String, String>()
    val addressStructure = FormItem<String, String>()
    val phoneNumber = FormItem<String, String>()

    val checkAgree = FormItem<Boolean, Boolean>()

    override fun navigateToPreviewDetails() {
        val nickNameInput = nickName.validate(
            validatorBlock = { it?.let(Validator::isNickNameValid).orFalse() },
            outputBlock = { it }
        )

        val firstNameInput = firstName.validate(
            validatorBlock = { it?.let(Validator::isFirstNameValid).orFalse() },
            outputBlock = { it }
        )

        val lastNameInput = lastName.validate(
            validatorBlock = { it?.let(Validator::isLastNameValid).orFalse() },
            outputBlock = { it }
        )

        val checkBoxStatus = checkAgree.validate(
            validatorBlock = { it?.let(Validator::isAgreeOnTerms).orFalse() },
            outputBlock = { it }
        )

        when {
            nickNameInput == null -> {}
            firstNameInput == null -> {}
            lastNameInput == null -> {}
            checkBoxStatus == null -> {}
            else -> {
                val address = AddressParam(
                    postalCode = postalCode.input.get().orEmpty(),
                    city = addressMunicipality.input.get().orEmpty(),
                    number = addressComplete.input.get().orEmpty(),
                    structure = addressStructure.input.get().orEmpty(),
                    prefecture = addressPrefecture.input.get().orEmpty()
                )

                val userParam = UserParam(
                    nickName = nickNameInput,
                    lastName = lastNameInput,
                    firstName = firstNameInput,
                    lastNameKana = kanaLastName.input.get().orEmpty(),
                    firstNameKana = kanaFirstName.input.get().orEmpty(),
                    phoneNumber = phoneNumber.input.get().orEmpty(),
                    gender = when (gender.get()) {
                        ConstantUtil.GENDER_FEMALE -> User.Gender.FEMALE
                        ConstantUtil.GENDER_MALE -> User.Gender.MALE
                        ConstantUtil.GENDER_UNANSWERED -> User.Gender.NO_ANSWER
                        else -> User.Gender.UNKNOWN
                    },
                    birthdate = birthdate.input.get(),
                    address = address
                )

                sendUiEvent(NavigateToPreviewDetails(userParam))
            }
        }
    }

    override fun navigateToAppTerms() = sendUiEvent(NavigateToAppTerms)

    override fun navigateToPrivacyPolicy() = sendUiEvent(NavigateToPrivacyPolicy)

    override fun openSelectBirthdate() {
        val uiEvent = NavigateToSelectBirthdate(birthdate.input.get())
        sendUiEvent(uiEvent)
    }

    override fun selectGenderWoman() {
        gender.set(ConstantUtil.GENDER_FEMALE)
    }

    override fun selectGenderMan() {
        gender.set(ConstantUtil.GENDER_MALE)
    }

    override fun selectGenderOther() {
        gender.set(ConstantUtil.GENDER_UNANSWERED)
    }

    override fun onCheckedAgree(cButton: CompoundButton, check: Boolean) {
        if (check) {
            checkAgree.input.set(true)
        } else {
            checkAgree.input.set(false)
        }
    }
}

interface SignUpInputDetailsUiEventListener {
    fun navigateToAppTerms()
    fun navigateToPrivacyPolicy()
    fun navigateToPreviewDetails()
    fun openSelectBirthdate()
    fun selectGenderWoman()
    fun selectGenderMan()
    fun selectGenderOther()
    fun onCheckedAgree(cButton: CompoundButton, check: Boolean)
}

sealed class SignUpInputDetailsUiEvent {
    data class NavigateToPreviewDetails(
        val userParam: UserParam
    ) : SignUpInputDetailsUiEvent()

    object NavigateToAppTerms : SignUpInputDetailsUiEvent()
    object NavigateToPrivacyPolicy : SignUpInputDetailsUiEvent()
    data class NavigateToSelectBirthdate(val param: BirthdateParam?) : SignUpInputDetailsUiEvent()
}