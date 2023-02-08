package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import team.standalone.core.data.util.test.ArtistTestUtil

class ArtistTest: ArtistTestUtil() {

    private lateinit var artist: Artist

    @Mock
    private lateinit var instant: Instant

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        artist = getTestArtist(instant)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUid() {
        Truth.assertThat(artist.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUid() {
        val actual = "abc123"
        Truth.assertThat(artist.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToChatColor() {
        Truth.assertThat(artist.chatColor).isEqualTo(expectedChatColor)
    }

    /**
     * Test case: should not match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToChatColor() {
        val actual = "#000000"
        Truth.assertThat(artist.chatColor).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToIcon() {
        Truth.assertThat(artist.icon).isEqualTo(expectedIcon)
    }

    /**
     * Test case: should not match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToIcon() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(artist.icon).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToNickName() {
        Truth.assertThat(artist.nickName).isEqualTo(expectedNickName)
    }

    /**
     * Test case: should not match the value of nickName to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToNickName() {
        val actual = "Admin John"
        Truth.assertThat(artist.nickName).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCreatedAt() {
        Truth.assertThat(artist.createdAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCreatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(artist.createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUpdatedAt() {
        Truth.assertThat(artist.updatedAt).isEqualTo(instant)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUpdatedAt() {
        val actual = Mockito.mock(Instant::class.java)
        Truth.assertThat(artist.updatedAt).isNotEqualTo(actual)
    }
}