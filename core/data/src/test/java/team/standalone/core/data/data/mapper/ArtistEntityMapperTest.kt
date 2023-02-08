package team.standalone.core.data.data.mapper

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import team.standalone.core.data.domain.model.Artist
import team.standalone.core.data.util.test.ArtistTestUtil
import team.standalone.core.database.room.model.entity.ArtistEntity

class ArtistEntityMapperTest: ArtistTestUtil() {

    private lateinit var artistEntityMapper: ArtistEntityMapper

    private lateinit var artistList: List<Artist>
    private lateinit var artist: Artist
    private lateinit var artistEntity: ArtistEntity

    @Mock
    private lateinit var instant: Instant

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        artistEntityMapper = ArtistEntityMapper()
        artistEntity = getTestArtistEntity(instant)
        artistList = artistEntityMapper.mapToDomain(
            getTestArtistEntityList(instant)
        )
        artist = artistEntityMapper.mapToDomain(artistEntity)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainUid() {
        Truth.assertThat(artist.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainUid() {
        val actual = "abc123"
        Truth.assertThat(artist.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainChatColor() {
        Truth.assertThat(artist.chatColor).isEqualTo(expectedChatColor)
    }

    /**
     * Test case: should not match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainChatColor() {
        val actual = "#000000"
        Truth.assertThat(artist.chatColor).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainIcon() {
        Truth.assertThat(artist.icon).isEqualTo(expectedIcon)
    }

    /**
     * Test case: should not match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainIcon() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(artist.icon).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainNickName() {
        Truth.assertThat(artist.nickName).isEqualTo(expectedNickName)
    }

    /**
     * Test case: should not match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainNickName() {
        val actual = "Admin John"
        Truth.assertThat(artist.nickName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainCreatedAt() {
        Truth.assertThat(artist.createdAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainCreatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(artist.createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainUpdatedAt() {
        Truth.assertThat(artist.updatedAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainUpdatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(artist.updatedAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListUid() {
        Truth.assertThat(artistList.first().uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListUid() {
        val actual = "abc123"
        Truth.assertThat(artistList.first().uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListChatColor() {
        Truth.assertThat(artistList.first().chatColor).isEqualTo(expectedChatColor)
    }

    /**
     * Test case: should not match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListChatColor() {
        val actual = "#000000"
        Truth.assertThat(artistList.first().chatColor).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListIcon() {
        Truth.assertThat(artistList.first().icon).isEqualTo(expectedIcon)
    }

    /**
     * Test case: should not match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListIcon() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(artistList.first().icon).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListNickName() {
        Truth.assertThat(artistList.first().nickName).isEqualTo(expectedNickName)
    }

    /**
     * Test case: should not match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListNickName() {
        val actual = "Admin John"
        Truth.assertThat(artistList.first().nickName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListCreatedAt() {
        Truth.assertThat(artistList.first().createdAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListCreatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(artistList.first().createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToMapToDomainListUpdatedAt() {
        Truth.assertThat(artistList.first().updatedAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToMapToDomainListUpdatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(artistList.first().updatedAt).isNotEqualTo(actual)
    }
}