package team.standalone.core.datastore.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class UserProtoTest {

    private lateinit var userProto: UserProto

    private val expectedUid: String = "abc123"
    private val expectedEmail: String = "test@fumiya.com"
    private val expectedIsEmailVerified: Boolean = true

    @Before
    fun setup() {
        userProto = UserProto(
            uid = expectedUid,
            email = expectedEmail,
            isEmailVerified = expectedIsEmailVerified
        )
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUid() {
        Truth.assertThat(userProto.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUid() {
        val actual = "123abc"
        Truth.assertThat(userProto.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToEmail() {
        Truth.assertThat(userProto.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToEmail() {
        val actual = "info@fumiya.com"
        Truth.assertThat(userProto.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of isEmailVerified to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToIsEmailVerified() {
        Truth.assertThat(userProto.isEmailVerified).isEqualTo(expectedIsEmailVerified)
    }

    /**
     * Test case: should not match the value of isEmailVerified to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToIsEmailVerified() {
        val actual = false
        Truth.assertThat(userProto.isEmailVerified).isNotEqualTo(actual)
    }
}