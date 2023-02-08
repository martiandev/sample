package team.standalone.core.data.data.mapper

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import team.standalone.core.common.util.ConstantUtil
import team.standalone.core.data.domain.model.User
import team.standalone.core.data.util.test.UserTestUtil
import team.standalone.core.database.room.model.entity.UserEntity
import team.standalone.core.network.model.UserRemote

class UserRemoteMapperTest: UserTestUtil() {

    private lateinit var userRemoteMapper: UserRemoteMapper

    private lateinit var userList: List<UserEntity>
    private lateinit var userEntity: UserEntity
    private lateinit var userRemote: UserRemote

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRemoteMapper = UserRemoteMapper()
        userRemote = getTestUserRemote()
        userList = userRemoteMapper.mapToEntity(
            getTestUserRemoteList()
        )
        userEntity = userRemoteMapper.mapToEntity(userRemote)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityUid() {
        Truth.assertThat(userEntity.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityUid() {
        val actual = "abc123"
        Truth.assertThat(userEntity.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityEmail() {
        Truth.assertThat(userEntity.email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityEmail() {
        val actual = "admin@fumiya.com"
        Truth.assertThat(userEntity.email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityPhotoUrl() {
        Truth.assertThat(userEntity.photoUrl).isEqualTo(expectedPhotoUrl)
    }

    /**
     * Test case: should not match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityPhotoUrl() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(userEntity.photoUrl).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityNickname() {
        Truth.assertThat(userEntity.nickname).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityNickname() {
        val actual = "Admin John"
        Truth.assertThat(userEntity.nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityFirstName() {
        Truth.assertThat(userEntity.firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityFirstName() {
        val actual = "Mike"
        Truth.assertThat(userEntity.firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityLastName() {
        Truth.assertThat(userEntity.lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityLastName() {
        val actual = "Lee"
        Truth.assertThat(userEntity.lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityFirstNameKana() {
        Truth.assertThat(userEntity.firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(userEntity.firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityLastNameKana() {
        Truth.assertThat(userEntity.lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityLastNameKana() {
        val actual = "リー"
        Truth.assertThat(userEntity.lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityMemberNumber() {
        Truth.assertThat(userEntity.memberNumber).isEqualTo(expectedMemberNumber)
    }

    /**
     * Test case: should not match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityMemberNumber() {
        val actual = "654321"
        Truth.assertThat(userEntity.memberNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityPhoneNumber() {
        Truth.assertThat(userEntity.phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(userEntity.phoneNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityGender() {
        Truth.assertThat(userEntity.gender).isEqualTo(ConstantUtil.GENDER_LABEL_MALE)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityGender() {
        val actual = User.Gender.FEMALE
        Truth.assertThat(userEntity.gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityWithdraw() {
        Truth.assertThat(userEntity.withdraw).isEqualTo(expectedWithdraw)
    }

    /**
     * Test case: should not match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityWithdraw() {
        val actual = false
        Truth.assertThat(userEntity.withdraw).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListUid() {
        Truth.assertThat(userList.first().uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListUid() {
        val actual = "abc123"
        Truth.assertThat(userList.first().uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListEmail() {
        Truth.assertThat(userList.first().email).isEqualTo(expectedEmail)
    }

    /**
     * Test case: should not match the value of email to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListEmail() {
        val actual = "admin@fumiya.com"
        Truth.assertThat(userList.first().email).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListPhotoUrl() {
        Truth.assertThat(userList.first().photoUrl).isEqualTo(expectedPhotoUrl)
    }

    /**
     * Test case: should not match the value of photoUrl to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListPhotoUrl() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(userList.first().photoUrl).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListNickname() {
        Truth.assertThat(userList.first().nickname).isEqualTo(expectedNickname)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListNickname() {
        val actual = "Admin John"
        Truth.assertThat(userList.first().nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListFirstName() {
        Truth.assertThat(userList.first().firstName).isEqualTo(expectedFirstName)
    }

    /**
     * Test case: should not match the value of firstName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListFirstName() {
        val actual = "Mike"
        Truth.assertThat(userList.first().firstName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListLastName() {
        Truth.assertThat(userList.first().lastName).isEqualTo(expectedLastName)
    }

    /**
     * Test case: should not match the value of lastName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListLastName() {
        val actual = "Lee"
        Truth.assertThat(userList.first().lastName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListFirstNameKana() {
        Truth.assertThat(userList.first().firstNameKana).isEqualTo(expectedFirstNameKana)
    }

    /**
     * Test case: should not match the value of firstNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListFirstNameKana() {
        val actual = "マイク"
        Truth.assertThat(userList.first().firstNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListLastNameKana() {
        Truth.assertThat(userList.first().lastNameKana).isEqualTo(expectedLastNameKana)
    }

    /**
     * Test case: should not match the value of lastNameKana to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListLastNameKana() {
        val actual = "リー"
        Truth.assertThat(userList.first().lastNameKana).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListMemberNumber() {
        Truth.assertThat(userList.first().memberNumber).isEqualTo(expectedMemberNumber)
    }

    /**
     * Test case: should not match the value of memberNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListMemberNumber() {
        val actual = "654321"
        Truth.assertThat(userList.first().memberNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListPhoneNumber() {
        Truth.assertThat(userList.first().phoneNumber).isEqualTo(expectedPhoneNumber)
    }

    /**
     * Test case: should not match the value of phoneNumber to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListPhoneNumber() {
        val actual = "71230972"
        Truth.assertThat(userList.first().phoneNumber).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListGender() {
        Truth.assertThat(userList.first().gender).isEqualTo(ConstantUtil.GENDER_LABEL_MALE)
    }

    /**
     * Test case: should not match the value of gender to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListGender() {
        val actual = User.Gender.FEMALE
        Truth.assertThat(userList.first().gender).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListWithdraw() {
        Truth.assertThat(userList.first().withdraw).isEqualTo(expectedWithdraw)
    }

    /**
     * Test case: should not match the value of withdraw to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListWithdraw() {
        val actual = false
        Truth.assertThat(userList.first().withdraw).isNotEqualTo(actual)
    }
}