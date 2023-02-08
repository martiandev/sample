package team.standalone.core.data.data.mapper

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.data.domain.model.param.UserParam
import team.standalone.core.data.util.test.UserTestUtil
import team.standalone.core.network.model.request.UserRequest

class UserParamMapperTest: UserTestUtil() {

    private lateinit var userParamMapper: UserParamMapper

    private lateinit var userRequest: UserRequest
    private lateinit var userParam: UserParam
    
    @Before
    fun setup() {
        userParamMapper = UserParamMapper()
        userParam = getTestUserParam()
        userRequest = userParamMapper.map(userParam)
    }

    /**
     * Test case: should match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapNickName() {
        Truth.assertThat(userRequest.nickName).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapNickName() {
        val actual = "Admin John"
        Truth.assertThat(userRequest.nickName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapFirstName() {
        Truth.assertThat(userRequest.firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapFirstName() {
        val actual = "Mike"
        Truth.assertThat(userRequest.firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapLastName() {
        Truth.assertThat(userRequest.lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapLastName() {
        val actual = "Lee"
        Truth.assertThat(userRequest.lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapFirstNameKana() {
        Truth.assertThat(userRequest.firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(userRequest.firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapLastNameKana() {
        Truth.assertThat(userRequest.lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapLastNameKana() {
        val actual = "リー"
        Truth.assertThat(userRequest.lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapGender() {
        Truth.assertThat(userRequest.gender).isEqualTo(ConstantUtil.GENDER_LABEL_MALE)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapGender() {
        val actual = ConstantUtil.GENDER_LABEL_FEMALE
        Truth.assertThat(userRequest.gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapPhoneNumber() {
        Truth.assertThat(userRequest.phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(userRequest.phoneNumber).isNotEqualTo(actual)
    }
}