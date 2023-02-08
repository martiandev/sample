package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import org.junit.Test

class BulletinTest {

    private lateinit var bulletin: Bulletin

    /**
     * Test case: should match the value of actionType to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToActionType() {
        val expected = "bulletin"
        bulletin = Bulletin(actionType = expected)
        Truth.assertThat(bulletin.actionType).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of actionType to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToActionType() {
        val expected = "bulletin"
        val actual = "chat"
        bulletin = Bulletin(actionType = expected)
        Truth.assertThat(bulletin.actionType).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of collection to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCollection() {
        val expected = "bulletin"
        bulletin = Bulletin(collection = expected)
        Truth.assertThat(bulletin.collection).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of collection to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCollection() {
        val expected = "bulletin"
        val actual = "chat"
        bulletin = Bulletin(collection = expected)
        Truth.assertThat(bulletin.collection).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of content to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToContent() {
        val expected = "Hello World"
        bulletin = Bulletin(content = expected)
        Truth.assertThat(bulletin.content).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of content to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToContent() {
        val expected = "Hello World"
        val actual = "Hello"
        bulletin = Bulletin(content = expected)
        Truth.assertThat(bulletin.content).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of image to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToImage() {
        val expected = "base64://abc"
        bulletin = Bulletin(image = expected)
        Truth.assertThat(bulletin.image).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of image to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToImage() {
        val expected = "base64://abc"
        val actual = "base64://xyz"
        bulletin = Bulletin(image = expected)
        Truth.assertThat(bulletin.image).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of title to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToTitle() {
        val expected = "Hello World"
        bulletin = Bulletin(title = expected)
        Truth.assertThat(bulletin.title).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of title to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToTitle() {
        val expected = "Hello World"
        val actual = "Hello"
        bulletin = Bulletin(title = expected)
        Truth.assertThat(bulletin.title).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToUid() {
        val expected = "abc"
        bulletin = Bulletin(uid = expected)
        Truth.assertThat(bulletin.uid).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of uid to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToUid() {
        val expected = "abc"
        val actual = "xyz"
        bulletin = Bulletin(uid = expected)
        Truth.assertThat(bulletin.uid).isNotEqualTo(actual)
    }
}