package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import team.standalone.core.data.util.test.UserTestUtil

class UserTest: UserTestUtil() {

    private lateinit var user: User

    @Mock
    private lateinit var instant: Instant

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        user = getTestUser(instant)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUid() {
        Truth.assertThat(user.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUid() {
        val actual = "abc123"
        Truth.assertThat(user.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToEmail() {
        Truth.assertThat(user.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToEmail() {
        val actual = "admin@fumiya.com"
        Truth.assertThat(user.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPhotoUrl() {
        Truth.assertThat(user.photoUrl).isEqualTo(expectedPhotoUrl)
    }

    /**
     * Test case: should not match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPhotoUrl() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(user.photoUrl).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToNickname() {
        Truth.assertThat(user.nickname).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToNickname() {
        val actual = "Admin John"
        Truth.assertThat(user.nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToFirstName() {
        Truth.assertThat(user.firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToFirstName() {
        val actual = "Mike"
        Truth.assertThat(user.firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLastName() {
        Truth.assertThat(user.lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToLastName() {
        val actual = "Lee"
        Truth.assertThat(user.lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToFirstNameKana() {
        Truth.assertThat(user.firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(user.firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToLastNameKana() {
        Truth.assertThat(user.lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToLastNameKana() {
        val actual = "リー"
        Truth.assertThat(user.lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMemberNumber() {
        Truth.assertThat(user.memberNumber).isEqualTo(expectedMemberNumber)
    }

    /**
     * Test case: should not match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMemberNumber() {
        val actual = "654321"
        Truth.assertThat(user.memberNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToPhoneNumber() {
        Truth.assertThat(user.phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(user.phoneNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToGender() {
        Truth.assertThat(user.gender).isEqualTo(expectedGender)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToGender() {
        val actual = User.Gender.FEMALE
        Truth.assertThat(user.gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToWithdraw() {
        Truth.assertThat(user.withdraw).isEqualTo(expectedWithdraw)
    }

    /**
     * Test case: should not match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToWithdraw() {
        val actual = false
        Truth.assertThat(user.withdraw).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCreatedAt() {
        Truth.assertThat(user.createdAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCreatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(user.createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUpdatedAt() {
        Truth.assertThat(user.updatedAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUpdatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(user.updatedAt).isNotEqualTo(actual)
    }
}