package team.standalone.core.network.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.network.util.test.UserTestUtil

class UserRemoteTest: UserTestUtil() {

    private lateinit var userRemote: UserRemote

    @Before
    fun setup() {
        userRemote = getTestUserRemote()
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityUid() {
        Truth.assertThat(userRemote.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityUid() {
        val actual = "abc123"
        Truth.assertThat(userRemote.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityEmail() {
        Truth.assertThat(userRemote.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityEmail() {
        val actual = "admin@fumiya.com"
        Truth.assertThat(userRemote.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityIcon() {
        Truth.assertThat(userRemote.icon).isEqualTo(expectedPhotoUrl)
    }

    /**
     * Test case: should not match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityIcon() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(userRemote.icon).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityNickname() {
        Truth.assertThat(userRemote.nickname).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityNickname() {
        val actual = "Admin John"
        Truth.assertThat(userRemote.nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityFirstName() {
        Truth.assertThat(userRemote.firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityFirstName() {
        val actual = "Mike"
        Truth.assertThat(userRemote.firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityLastName() {
        Truth.assertThat(userRemote.lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityLastName() {
        val actual = "Lee"
        Truth.assertThat(userRemote.lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityFirstNameKana() {
        Truth.assertThat(userRemote.firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(userRemote.firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityLastNameKana() {
        Truth.assertThat(userRemote.lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityLastNameKana() {
        val actual = "リー"
        Truth.assertThat(userRemote.lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityMemberNumber() {
        Truth.assertThat(userRemote.memberNumber).isEqualTo(expectedMemberNumber)
    }

    /**
     * Test case: should not match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityMemberNumber() {
        val actual = "654321"
        Truth.assertThat(userRemote.memberNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityPhoneNumber() {
        Truth.assertThat(userRemote.phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(userRemote.phoneNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityGender() {
        Truth.assertThat(userRemote.gender).isEqualTo(ConstantUtil.GENDER_LABEL_MALE)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityGender() {
        val actual = ConstantUtil.GENDER_LABEL_FEMALE
        Truth.assertThat(userRemote.gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityWithdraw() {
        Truth.assertThat(userRemote.withdraw).isEqualTo(expectedWithdraw)
    }

    /**
     * Test case: should not match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityWithdraw() {
        val actual = false
        Truth.assertThat(userRemote.withdraw).isNotEqualTo(actual)
    }
}