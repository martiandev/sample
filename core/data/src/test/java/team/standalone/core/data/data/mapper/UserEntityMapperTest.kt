package team.standalone.core.data.data.mapper

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.util.test.UserTestUtil
import team.standalone.core.database.room.model.entity.UserEntity

class UserEntityMapperTest: UserTestUtil() {

    private lateinit var userEntityMapper: UserEntityMapper

    private lateinit var userList: List<User>
    private lateinit var user: User
    private lateinit var userEntity: UserEntity

    @Mock
    private lateinit var instant: Instant

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userEntityMapper = UserEntityMapper()
        userEntity = getTestUserEntity(instant)
        userList = userEntityMapper.mapToDomain(
            getTestUserEntityList(instant)
        )
        user = userEntityMapper.mapToDomain(userEntity)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainUid() {
        Truth.assertThat(user.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainUid() {
        val actual = "abc123"
        Truth.assertThat(user.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainEmail() {
        Truth.assertThat(user.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainEmail() {
        val actual = "admin@fumiya.com"
        Truth.assertThat(user.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainPhotoUrl() {
        Truth.assertThat(user.photoUrl).isEqualTo(expectedPhotoUrl)
    }

    /**
     * Test case: should not match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainPhotoUrl() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(user.photoUrl).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainNickname() {
        Truth.assertThat(user.nickname).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainNickname() {
        val actual = "Admin John"
        Truth.assertThat(user.nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainFirstName() {
        Truth.assertThat(user.firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainFirstName() {
        val actual = "Mike"
        Truth.assertThat(user.firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainLastName() {
        Truth.assertThat(user.lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainLastName() {
        val actual = "Lee"
        Truth.assertThat(user.lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainFirstNameKana() {
        Truth.assertThat(user.firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(user.firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainLastNameKana() {
        Truth.assertThat(user.lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainLastNameKana() {
        val actual = "リー"
        Truth.assertThat(user.lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainMemberNumber() {
        Truth.assertThat(user.memberNumber).isEqualTo(expectedMemberNumber)
    }

    /**
     * Test case: should not match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainMemberNumber() {
        val actual = "654321"
        Truth.assertThat(user.memberNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainPhoneNumber() {
        Truth.assertThat(user.phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(user.phoneNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainGender() {
        Truth.assertThat(user.gender).isEqualTo(expectedGender)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainGender() {
        val actual = User.Gender.FEMALE
        Truth.assertThat(user.gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainWithdraw() {
        Truth.assertThat(user.withdraw).isEqualTo(expectedWithdraw)
    }

    /**
     * Test case: should not match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainWithdraw() {
        val actual = false
        Truth.assertThat(user.withdraw).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainCreatedAt() {
        Truth.assertThat(user.createdAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainCreatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(user.createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainUpdatedAt() {
        Truth.assertThat(user.updatedAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainUpdatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(user.updatedAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListUid() {
        Truth.assertThat(userList.first().uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListUid() {
        val actual = "abc123"
        Truth.assertThat(userList.first().uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListEmail() {
        Truth.assertThat(userList.first().email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListEmail() {
        val actual = "admin@fumiya.com"
        Truth.assertThat(userList.first().email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListPhotoUrl() {
        Truth.assertThat(userList.first().photoUrl).isEqualTo(expectedPhotoUrl)
    }

    /**
     * Test case: should not match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListPhotoUrl() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(userList.first().photoUrl).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListNickname() {
        Truth.assertThat(userList.first().nickname).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListNickname() {
        val actual = "Admin John"
        Truth.assertThat(userList.first().nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListFirstName() {
        Truth.assertThat(userList.first().firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListFirstName() {
        val actual = "Mike"
        Truth.assertThat(userList.first().firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListLastName() {
        Truth.assertThat(userList.first().lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListLastName() {
        val actual = "Lee"
        Truth.assertThat(userList.first().lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListFirstNameKana() {
        Truth.assertThat(userList.first().firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(userList.first().firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListLastNameKana() {
        Truth.assertThat(userList.first().lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListLastNameKana() {
        val actual = "リー"
        Truth.assertThat(userList.first().lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListMemberNumber() {
        Truth.assertThat(userList.first().memberNumber).isEqualTo(expectedMemberNumber)
    }

    /**
     * Test case: should not match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListMemberNumber() {
        val actual = "654321"
        Truth.assertThat(userList.first().memberNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListPhoneNumber() {
        Truth.assertThat(userList.first().phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(userList.first().phoneNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListGender() {
        Truth.assertThat(userList.first().gender).isEqualTo(expectedGender)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListGender() {
        val actual = User.Gender.FEMALE
        Truth.assertThat(userList.first().gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListWithdraw() {
        Truth.assertThat(userList.first().withdraw).isEqualTo(expectedWithdraw)
    }

    /**
     * Test case: should not match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListWithdraw() {
        val actual = false
        Truth.assertThat(userList.first().withdraw).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListCreatedAt() {
        Truth.assertThat(userList.first().createdAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListCreatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(userList.first().createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListUpdatedAt() {
        Truth.assertThat(userList.first().updatedAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListUpdatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(userList.first().updatedAt).isNotEqualTo(actual)
    }
}