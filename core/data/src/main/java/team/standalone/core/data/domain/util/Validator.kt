package team.standalone.core.data.domain.util

import androidx.core.util.PatternsCompat
import team.standalone.core.common.extension.orFalse
import java.util.regex.Pattern

object Validator {

    fun isEmailValid(input: String?): Boolean {
        return !input.isNullOrBlank() && isEmailValidFormat(input)
    }

    fun isEmailValidFormat(input: String?): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(input.toString()).matches()
    }

    fun isPasswordValidFormat(input: String?): Boolean {
        return Pattern.compile("^(?=.*?[a-zA-Z])(?=.*?\\d)[a-zA-Z\\d]{8,16}\$")
            .matcher(input.toString())
            .matches()
    }

    fun isEmailConfirmationValid(email: String, input: String): Boolean {
        return email == input
    }

    fun isPasswordValid(input: String?): Boolean {
        return !input.isNullOrBlank() && isPasswordValidFormat(input)
    }

    fun isPasswordConfirmationValid(password: String, input: String): Boolean {
        return password == input
    }

    fun isNickNameValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isFirstNameValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isLastNameValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isFirstNameKanaValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isLastNameKanaValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isBirthdateValid(day: Int, month: Int, year: Int): Boolean {
        // todo change this
        return true
    }

    fun isAddressPostalCodeValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isAddressPrefectureValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isAddressCityValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isAddressNumberValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isAddressStructureValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isPhoneNumberValid(input: String?): Boolean {
        return !input.isNullOrBlank()
    }

    fun isAgreeOnTerms(isAgree: Boolean?): Boolean {
        return isAgree.orFalse()
    }
}