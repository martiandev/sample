package team.standalone.core.network.model.request

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.network.firebase.util.FirebaseProperty
import team.standalone.core.network.util.test.UserTestUtil

class UserRequestTest: UserTestUtil() {

    private lateinit var userRequest: UserRequest
    private lateinit var userRequestHashMap: HashMap<String, Any>

    @Before
    fun setup() {
        userRequest = getTestUserRequest()
        userRequestHashMap = userRequest.toMap()
    }

    /**
     * Test case: should match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToNickName() {
        Truth.assertThat(userRequest.nickName).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToNickName() {
        val actual = "Admin John"
        Truth.assertThat(userRequest.nickName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToFirstName() {
        Truth.assertThat(userRequest.firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToFirstName() {
        val actual = "Mike"
        Truth.assertThat(userRequest.firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToLastName() {
        Truth.assertThat(userRequest.lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToLastName() {
        val actual = "Lee"
        Truth.assertThat(userRequest.lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToFirstNameKana() {
        Truth.assertThat(userRequest.firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(userRequest.firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToLastNameKana() {
        Truth.assertThat(userRequest.lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToLastNameKana() {
        val actual = "リー"
        Truth.assertThat(userRequest.lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToPhoneNumber() {
        Truth.assertThat(userRequest.phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(userRequest.phoneNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToGender() {
        Truth.assertThat(userRequest.gender).isEqualTo(expectedGender)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToGender() {
        val actual = ConstantUtil.GENDER_LABEL_FEMALE
        Truth.assertThat(userRequest.gender).isNotEqualTo(actual)
    }

    @Test
    fun x() {
        val result = userRequest.toMap()
        Truth.assertThat(result[FirebaseProperty.NICKNAME]).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToHashMapNickName() {
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.NICKNAME])
            .isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToHashMapNickName() {
        val actual = "Admin John"
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.NICKNAME])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToHashMapFirstName() {
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.FIRST_NAME])
            .isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToHashMapFirstName() {
        val actual = "Mike"
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.FIRST_NAME])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToHashMapLastName() {
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.LAST_NAME])
            .isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToHashMapLastName() {
        val actual = "Lee"
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.LAST_NAME])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToHashMapFirstNameKana() {
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.FIRST_NAME_KANA])
            .isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToHashMapFirstNameKana() {
        val actual = "マイク"
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.FIRST_NAME_KANA])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToHashMapLastNameKana() {
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.LAST_NAME_KANA])
            .isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToHashMapLastNameKana() {
        val actual = "リー"
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.LAST_NAME_KANA])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToHashMapPhoneNumber() {
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.PHONE_NUMBER])
            .isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToHashMapPhoneNumber() {
        val actual = "71230972"
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.PHONE_NUMBER])
            .isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToHashMapGender() {
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.GENDER])
            .isEqualTo(expectedGender)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToHashMapGender() {
        val actual = ConstantUtil.GENDER_LABEL_FEMALE
        Truth
            .assertThat(userRequestHashMap[FirebaseProperty.GENDER])
            .isNotEqualTo(actual)
    }
}