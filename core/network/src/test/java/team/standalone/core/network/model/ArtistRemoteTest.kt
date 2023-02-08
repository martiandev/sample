package team.standalone.core.network.model

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import team.standalone.core.network.util.test.ArtistTestUtil
import java.util.*

class ArtistRemoteTest: ArtistTestUtil() {

    private lateinit var artistRemote: ArtistRemote

    @Before
    fun setup() {
        artistRemote = getTestArtistRemote()
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUid() {
        Truth.assertThat(artistRemote.uid).isEqualTo(expectedUid)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUid() {
        val actual = "abc123"
        Truth.assertThat(artistRemote.uid).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToChatColor() {
        Truth.assertThat(artistRemote.chatColor).isEqualTo(expectedChatColor)
    }

    /**
     * Test case: should not match the value of chatColor to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToChatColor() {
        val actual = "#000000"
        Truth.assertThat(artistRemote.chatColor).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToIcon() {
        Truth.assertThat(artistRemote.icon).isEqualTo(expectedIcon)
    }

    /**
     * Test case: should not match the value of icon to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToIcon() {
        val actual = "data:/sample/png/base64.."
        Truth.assertThat(artistRemote.icon).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToNickname() {
        Truth.assertThat(artistRemote.nickname).isEqualTo(expectedNickName)
    }

    /**
     * Test case: should not match the value of nickname to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToNickname() {
        val actual = "Admin John"
        Truth.assertThat(artistRemote.nickname).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCreatedAt() {
        Truth.assertThat(artistRemote.createdAt).isEqualTo(expectedDate)
    }

    /**
     * Test case: should not match the value of createdAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCreatedAt() {
        val actual = Mockito.mock(Date::class.java)
        Truth.assertThat(artistRemote.createdAt).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUpdatedAt() {
        Truth.assertThat(artistRemote.updatedAt).isEqualTo(expectedDate)
    }

    /**
     * Test case: should not match the value of updatedAt to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUpdatedAt() {
        val actual = Mockito.mock(Date::class.java)
        Truth.assertThat(artistRemote.updatedAt).isNotEqualTo(actual)
    }
}