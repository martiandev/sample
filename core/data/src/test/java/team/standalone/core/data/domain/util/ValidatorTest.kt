package team.standalone.core.data.domain.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/** Unit tests validation functions for the [Validator] object*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ValidatorTest {

    /** valid email address format*
     * satisfies the email address format*/
    private val emailValid = "validemail@gmail.com"

    /** invalid email address format
     * does not satisfies the email address format*/
    private val emailInvalid = "invalid.com"

    /** valid password format
     * test1234 - satisfies the password format*/
    private val passwordValid = "test1234"

    /** invalid password format:
     * test123 - less that 8 characters length*/
    private val passwordInvalid = "test123"

    /**
     * Test case: [Validator.isEmailValid] returns true
     * @param: valid email address format
     * @return true
     */
    @Test
    fun shouldReturnTrueIfEmailIsValid() {
        assertTrue(Validator.isEmailValid(input = emailValid))
    }

    /**
     * Test case: [Validator.isEmailValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfEmailIsEmptyString() {
        assertFalse(Validator.isEmailValid(input = ""))
    }

    /**
     * Test case: [Validator.isEmailValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfEmailIsNull() {
        assertFalse(Validator.isEmailValid(input = null))
    }

    /**
     * Test case: [Validator.isEmailValid] returns false
     * @param: invalid email address format
     * @return false
     */
    @Test
    fun shouldReturnFalseEmailIsInvalid() {
        assertFalse(Validator.isEmailValid(input = emailInvalid))
    }

    /**
     * Test case: [Validator.isEmailValidFormat] returns true
     * @param: valid email address format
     * @return true
     */
    @Test
    fun shouldReturnTrueIfEmailIsValidFormat() {
        assertTrue(Validator.isEmailValidFormat(input = emailValid))
    }

    /**
     * Test case: [Validator.isEmailValidFormat] returns false
     * @param: invalid email address format
     * @return false
     */
    @Test
    fun shouldReturnFalseIfEmailIsInvalidFormat() {
        assertFalse(Validator.isEmailValidFormat(input = emailInvalid))
    }

    /**
     * Test case: [Validator.isPasswordValidFormat] returns true
     * @param: valid password format
     * @return true
     */
    @Test
    fun shouldReturnTrueIfPasswordIsValidFormat() {
        assertTrue(Validator.isPasswordValidFormat(passwordValid))
    }

    /**
     * Test case: [Validator.isPasswordValidFormat] returns false
     * @param: invalid password format
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPasswordIsInValidFormat() {
        assertFalse(Validator.isPasswordValidFormat(passwordInvalid))
    }

    /**
     * Test case: [Validator.isEmailConfirmationValid] returns true
     * @param: email address isEquals to user input
     * @return true
     */
    @Test
    fun shouldReturnTrueIfEmailConfirmationIsValid() {
        assertTrue(Validator.isEmailConfirmationValid(email = emailValid, input = emailValid))
    }

    /**
     * Test case: [Validator.isEmailConfirmationValid] returns false
     * @param: email address isNotEquals to user input
     * @return false
     */
    @Test
    fun shouldReturnFalseIfEmailConfirmationIsInValid() {
        assertFalse(
            Validator.isEmailConfirmationValid(
                email = emailValid,
                input = "fake@gmail.com"
            )
        )
    }

    /**
     * Test case: [Validator.isPasswordValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPasswordIsEmptyString() {
        assertFalse(Validator.isPasswordValid(input = ""))
    }

    /**
     * Test case: [Validator.isPasswordValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPasswordIsNull() {
        assertFalse(Validator.isPasswordValid(input = null))
    }

    /**
     * Test case: [Validator.isPasswordValid] returns true
     * @param: valid password format
     * @return true
     */
    @Test
    fun shouldReturnTrueIfPasswordIsValid() {
        assertTrue(Validator.isPasswordValid(passwordValid))
    }

    /**
     * Test case: [Validator.isPasswordValid] returns false
     * @param: invalid password format
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPasswordIsInValidLength() {
        assertFalse(Validator.isPasswordValid(passwordInvalid))
    }

    /**
     * Test case: [Validator.isPasswordConfirmationValid] returns true
     * @param: password isEquals to user input
     * @return true
     */
    @Test
    fun shouldReturnTrueIfPasswordConfirmationIsValid() {
        assertTrue(
            Validator.isPasswordConfirmationValid(
                password = passwordValid,
                input = passwordValid
            )
        )
    }

    /**
     * Test case: [Validator.isPasswordConfirmationValid] returns false
     * @param: password isNotEquals to user input
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPasswordConfirmationIsInvalid() {
        assertFalse(
            Validator.isPasswordConfirmationValid(
                password = passwordValid,
                input = "1234test"
            )
        )
    }

    /**
     * Test case: [Validator.isNickNameValid] returns true
     * @param: nickName isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfNickNameIsValid() {
        assertTrue(Validator.isNickNameValid("testNickName"))
    }

    /**
     * Test case: [Validator.isNickNameValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfNickNameIsEmptyString() {
        assertFalse(Validator.isNickNameValid(""))
    }

    /**
     * Test case: [Validator.isNickNameValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfNickNameIsNull() {
        assertFalse(Validator.isNickNameValid(null))
    }

    /**
     * Test case: [Validator.isFirstNameValid] returns true
     * @param: firstName isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfFirstNameIsValid() {
        assertTrue(Validator.isFirstNameValid("testFirstName"))
    }

    /**
     * Test case: [Validator.isFirstNameValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfFirstNameIsEmptyString() {
        assertFalse(Validator.isFirstNameValid(""))
    }

    /**
     * Test case: [Validator.isFirstNameValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfFirstNameIsNull() {
        assertFalse(Validator.isFirstNameValid(null))
    }

    /**
     * Test case: [Validator.isLastNameValid] returns true
     * @param: lastName isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfLastNameIsValid() {
        assertTrue(Validator.isLastNameValid("testLastName"))
    }

    /**
     * Test case: [Validator.isLastNameValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfLastNameIsEmptyString() {
        assertFalse(Validator.isLastNameValid(""))
    }

    /**
     * Test case: [Validator.isLastNameValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfLastNameIsNull() {
        assertFalse(Validator.isLastNameValid(null))
    }

    /**
     * Test case: [Validator.isFirstNameKanaValid] returns true
     * @param: kanaFirstName istNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfFirstNameKanaIsValid() {
        assertTrue(Validator.isFirstNameKanaValid("testKanaFirstName"))
    }

    /**
     * Test case: [Validator.isFirstNameKanaValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfFirstNameKanaIsEmptyString() {
        assertFalse(Validator.isFirstNameKanaValid(""))
    }

    /**
     * Test case: [Validator.isFirstNameKanaValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfFirstNameKanaIsNull() {
        assertFalse(Validator.isFirstNameKanaValid(null))
    }

    /**
     * Test case: [Validator.isLastNameKanaValid] returns true
     * @param: kanaLastName isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfLastNameKanaIsValid() {
        assertTrue(Validator.isLastNameKanaValid("testKanaLastName"))
    }

    /**
     * Test case: [Validator.isLastNameKanaValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfLastNameKanaIsEmptyString() {
        assertFalse(Validator.isLastNameKanaValid(""))
    }

    /**
     * Test case: [Validator.isLastNameKanaValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfLastNameKanaIsNull() {
        assertFalse(Validator.isLastNameKanaValid(null))
    }

    /**
     * Test case: [Validator.isAddressPostalCodeValid] returns true
     * @param: postalCode isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTruePostalCodeIsValid() {
        assertTrue(Validator.isAddressPostalCodeValid("0101010"))
    }

    /**
     * Test case: [Validator.isAddressPostalCodeValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPostalCodeIsEmptyString() {
        assertFalse(Validator.isAddressPostalCodeValid(""))
    }

    /**
     * Test case: [Validator.isAddressPostalCodeValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPostalCodeIsNull() {
        assertFalse(Validator.isAddressPostalCodeValid(null))
    }

    /**
     * Test case: [Validator.isAddressPrefectureValid] returns true
     * @param: addressPrefecture isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfAddressPrefectureIsValid() {
        assertTrue(Validator.isAddressPrefectureValid("東京都"))
    }

    /**
     * Test case: [Validator.isAddressPrefectureValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressPrefectureIsEmpty() {
        assertFalse(Validator.isAddressPrefectureValid(""))
    }

    /**
     * Test case: [Validator.isAddressPrefectureValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressPrefectureIsNull() {
        assertFalse(Validator.isAddressPrefectureValid(null))
    }

    /**
     * Test case: [Validator.isAddressCityValid] returns true
     * @param: addressCity isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfAddressCityIsValid() {
        assertTrue(Validator.isAddressCityValid("testCity"))
    }

    /**
     * Test case: [Validator.isAddressCityValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressCityIsEmptyString() {
        assertFalse(Validator.isAddressCityValid(""))
    }

    /**
     * Test case: [Validator.isAddressCityValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressCityIsNull() {
        assertFalse(Validator.isAddressCityValid(null))
    }

    /**
     * Test case: [Validator.isAddressNumberValid] returns true
     * @param: addressNumber isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfAddressNumberIsValid() {
        assertTrue(Validator.isAddressNumberValid("testAddressNumber"))
    }

    /**
     * Test case: [Validator.isAddressNumberValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressNumberIsEmptyString() {
        assertFalse(Validator.isAddressNumberValid(""))
    }

    /**
     * Test case: [Validator.isAddressNumberValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressNumberIsNull() {
        assertFalse(Validator.isAddressNumberValid(null))
    }

    /**
     * Test case: [Validator.isAddressStructureValid] returns true
     * @param: addressStructure isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfAddressStructureIsValid() {
        assertTrue(Validator.isAddressStructureValid("testAddressStructure"))
    }

    /**
     * Test case: [Validator.isAddressStructureValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressStructureIsEmptyString() {
        assertFalse(Validator.isAddressStructureValid(""))
    }

    /**
     * Test case: [Validator.isAddressStructureValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAddressStructureIsNull() {
        assertFalse(Validator.isAddressStructureValid(null))
    }

    /**
     * Test case: [Validator.isPhoneNumberValid] returns true
     * @param: phoneNumber isNotEmpty
     * @return true
     */
    @Test
    fun shouldReturnTrueIfPhoneNumberIsValid() {
        assertTrue(Validator.isPhoneNumberValid("09123456789"))
    }

    /**
     * Test case: [Validator.isPhoneNumberValid] returns false
     * @param: empty string
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPhoneNumberIsEmptyString() {
        assertFalse(Validator.isPhoneNumberValid(""))
    }

    /**
     * Test case: [Validator.isPhoneNumberValid] returns false
     * @param: null
     * @return false
     */
    @Test
    fun shouldReturnFalseIfPhoneNumberIsNull() {
        assertFalse(Validator.isPhoneNumberValid(null))
    }

    /**
     * Test case: [Validator.isAgreeOnTerms] returns true
     * @param: selected to true
     * @return true
     */
    @Test
    fun shouldReturnTrueIfAgreeOnTermsIsTrue() {
        assertTrue(Validator.isAgreeOnTerms(true))
    }

    /**
     * Test case: [Validator.isAgreeOnTerms] returns false
     * @param: selected to false
     * @return false
     */
    @Test
    fun shouldReturnFalseIfAgreeOnTermsIsFalse() {
        assertFalse(Validator.isAgreeOnTerms(false))
    }
}