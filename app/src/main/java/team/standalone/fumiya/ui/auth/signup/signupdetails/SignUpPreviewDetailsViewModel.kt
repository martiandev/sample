package team.standalone.fumiya.ui.auth.signup.signupdetails

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.common.util.LoadResult
import team.standalone.core.common.util.asLoadResult
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.domain.model.param.AddressParam
import team.standalone.core.data.domain.model.param.BirthdateParam
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.data.domain.usecase.SaveNewUserUseCase
import team.standalone.core.ui.util.PrefectureUtil.translatePrefectureToKanji
import team.standalone.core.ui.viewmodel.BaseAndroidViewModel
import team.standalone.fumiya.ui.auth.signup.signupdetails.SignUpPreviewDetailsUiEvent.*
import javax.inject.Inject

@HiltViewModel
class SignUpPreviewDetailsViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val saveNewUserUseCase: SaveNewUserUseCase,
) : BaseAndroidViewModel<SignUpPreviewDetailsUiEvent>(application), SignUpPreviewDetailsUiEventListener {
    private val _registerUserResult = Channel<LoadResult<User>>()
    val registerUserResult = _registerUserResult.receiveAsFlow()

    val userData = ObservableField<UserParam>()
    val gender = ObservableField<Int>()

    fun kanaVisibility(): Boolean {
        return !userData.get()?.firstNameKana.isNullOrEmpty() || !userData.get()?.lastNameKana.isNullOrEmpty()
    }

    init {
        val user = savedStateHandle.get<UserParam>("user")
        userData.set(user)
        gender.set(
            when (userData.get()?.gender) {
                User.Gender.FEMALE -> ConstantUtil.GENDER_FEMALE
                User.Gender.MALE -> ConstantUtil.GENDER_MALE
                User.Gender.NO_ANSWER -> ConstantUtil.GENDER_UNANSWERED
                else -> null
            }
        )
    }

    override fun navigateToSubscriptionScreen() {
        userData.get()?.let { registerNewUser(it) }
    }

    override fun navigateToBackToInputDetails() {
        sendUiEvent(NavigateToBackToInputDetails)
    }

    private fun registerNewUser(userData: UserParam) {
        val birthDate = userData.birthdate?.let {
            BirthdateParam(day = it.day, month = it.month, year = it.year)
        } ?: BirthdateParam(day = -1, month = -1, year = -1)

        val address = AddressParam(
            postalCode = userData.address.postalCode,
            city = userData.address.city,
            number = userData.address.number,
            structure = userData.address.structure,
            prefecture =  translatePrefectureToKanji(
                context = getApplication(),
                prefecture = userData.address.prefecture
            )
        )

        val userParam = UserParam(
            nickName = userData.nickName,
            lastName = userData.lastName,
            firstName = userData.firstName,
            lastNameKana = userData.lastNameKana,
            firstNameKana = userData.firstNameKana,
            phoneNumber = userData.phoneNumber,
            gender = userData.gender,
            birthdate = birthDate,
            address = address
        )

        runUseCase {
            _registerUserResult.trySend(LoadResult.Loading())
            val result = saveNewUserUseCase(userParam)
            _registerUserResult.trySend(result.asLoadResult())
        }
    }

    override fun navigateToAppTerms() = sendUiEvent(NavigateToAppTerms)

    override fun navigateToPrivacyPolicy() = sendUiEvent(NavigateToPrivacyPolicy)
}

interface SignUpPreviewDetailsUiEventListener {
    fun navigateToSubscriptionScreen()
    fun navigateToBackToInputDetails()
    fun navigateToAppTerms()
    fun navigateToPrivacyPolicy()
}

sealed class SignUpPreviewDetailsUiEvent {
    object NavigateToSubscriptionScreen : SignUpPreviewDetailsUiEvent()
    object NavigateToBackToInputDetails : SignUpPreviewDetailsUiEvent()
    object NavigateToAppTerms : SignUpPreviewDetailsUiEvent()
    object NavigateToPrivacyPolicy : SignUpPreviewDetailsUiEvent()
}