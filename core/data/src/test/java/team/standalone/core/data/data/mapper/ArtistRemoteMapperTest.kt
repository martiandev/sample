package team.standalone.core.data.data.mapper

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import team.standalone.core.data.util.test.ArtistTestUtil
import team.standalone.core.database.room.model.entity.ArtistEntity
import team.standalone.core.network.model.ArtistRemote
import java.util.*

class ArtistRemoteMapperTest: ArtistTestUtil() {

    private lateinit var artistRemoteMapper: ArtistRemoteMapper

    private lateinit var artistEntityList: List<ArtistEntity>
    private lateinit var artistRemote: ArtistRemote
    private lateinit var artistEntity: ArtistEntity

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        artistRemoteMapper = ArtistRemoteMapper()
        artistRemote = getTestArtistRemote()
        artistEntityList = artistRemoteMapper.mapToEntity(
            getTestArtistRemoteList()
        )
        artistEntity = artistRemoteMapper.mapToEntity(artistRemote)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityUid() {
        Truth.assertThat(artistEntity.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityUid() {
        val actual = "abc123"
        Truth.assertThat(artistEntity.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityChatColor() {
        Truth.assertThat(artistEntity.chatColor).isEqualTo(expectedChatColor)
    }

    /**
     * Test case: should not match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityChatColor() {
        val actual = "#000000"
        Truth.assertThat(artistEntity.chatColor).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityIcon() {
        Truth.assertThat(artistEntity.icon).isEqualTo(expectedIcon)
    }

    /**
     * Test case: should not match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityIcon() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(artistEntity.icon).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityNickName() {
        Truth.assertThat(artistEntity.nickname).isEqualTo(expectedNickName)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityNickName() {
        val actual = "Admin John"
        Truth.assertThat(artistEntity.nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityCreatedAt() {
        val expected = expectedDate.time.let(Instant::fromEpochMilliseconds)
        Truth.assertThat(artistEntity.createdAt).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityCreatedAt() {
        val actual = Mockito.mock(Date::class.java)
        Truth.assertThat(artistEntity.createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityUpdatedAt() {
        val expected = expectedDate.time.let(Instant::fromEpochMilliseconds)
        Truth.assertThat(artistEntity.updatedAt).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityUpdatedAt() {
        val actual = Mockito.mock(Date::class.java)
        Truth.assertThat(artistEntity.updatedAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListUid() {
        Truth.assertThat(artistEntityList.first().uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListUid() {
        val actual = "abc123"
        Truth.assertThat(artistEntityList.first().uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListChatColor() {
        Truth.assertThat(artistEntityList.first().chatColor).isEqualTo(expectedChatColor)
    }

    /**
     * Test case: should not match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListChatColor() {
        val actual = "#000000"
        Truth.assertThat(artistEntityList.first().chatColor).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListIcon() {
        Truth.assertThat(artistEntityList.first().icon).isEqualTo(expectedIcon)
    }

    /**
     * Test case: should not match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListIcon() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(artistEntityList.first().icon).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListNickName() {
        Truth.assertThat(artistEntityList.first().nickname).isEqualTo(expectedNickName)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListNickName() {
        val actual = "Admin John"
        Truth.assertThat(artistEntityList.first().nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListCreatedAt() {
        val expected = expectedDate.time.let(Instant::fromEpochMilliseconds)
        Truth.assertThat(artistEntityList.first().createdAt).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListCreatedAt() {
        val actual = Mockito.mock(Date::class.java)
        Truth.assertThat(artistEntityList.first().createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToEntityListUpdatedAt() {
        val expected = expectedDate.time.let(Instant::fromEpochMilliseconds)
        Truth.assertThat(artistEntityList.first().updatedAt).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToEntityListUpdatedAt() {
        val actual = Mockito.mock(Date::class.java)
        Truth.assertThat(artistEntityList.first().updatedAt).isNotEqualTo(actual)
    }
}