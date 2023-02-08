package team.standalone.core.data.domain.model.param

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.util.test.UserTestUtil

class UserParamTest: UserTestUtil() {

    private lateinit var userParam: UserParam

    @Before
    fun setup() {
        userParam = getTestUserParam()
    }

    /**
     * Test case: should match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToNickName() {
        Truth.assertThat(userParam.nickName).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToNickName() {
        val actual = "Admin John"
        Truth.assertThat(userParam.nickName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToFirstName() {
        Truth.assertThat(userParam.firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToFirstName() {
        val actual = "Mike"
        Truth.assertThat(userParam.firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLastName() {
        Truth.assertThat(userParam.lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToLastName() {
        val actual = "Lee"
        Truth.assertThat(userParam.lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToFirstNameKana() {
        Truth.assertThat(userParam.firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(userParam.firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLastNameKana() {
        Truth.assertThat(userParam.lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToLastNameKana() {
        val actual = "リー"
        Truth.assertThat(userParam.lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToGender() {
        Truth.assertThat(userParam.gender).isEqualTo(expectedGender)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToGender() {
        val actual = User.Gender.FEMALE
        Truth.assertThat(userParam.gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPhoneNumber() {
        Truth.assertThat(userParam.phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(userParam.phoneNumber).isNotEqualTo(actual)
    }
}