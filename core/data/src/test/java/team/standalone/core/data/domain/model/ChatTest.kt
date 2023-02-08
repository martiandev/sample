package team.standalone.core.data.domain.model

import com.google.common.truth.Truth
import org.junit.Test

class ChatTest {

    private lateinit var chat: Chat

    /**
     * Test case: should match the value of actionType to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToActionType() {
        val expected = "chat"
        chat = Chat(actionType = expected)
        Truth.assertThat(chat.actionType).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of actionType to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToActionType() {
        val expected = "chat"
        val actual = "bulletin"
        chat = Chat(actionType = expected)
        Truth.assertThat(chat.actionType).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of collection to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCollection() {
        val expected = "chat"
        chat = Chat(collection = expected)
        Truth.assertThat(chat.collection).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of collection to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCollection() {
        val expected = "chat"
        val actual = "bulletin"
        chat = Chat(collection = expected)
        Truth.assertThat(chat.collection).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of groupId to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToGroupId() {
        val expected = "abc"
        chat = Chat(groupId = expected)
        Truth.assertThat(chat.groupId).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of groupId to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToGroupId() {
        val expected = "abc"
        val actual = "xyz"
        chat = Chat(groupId = expected)
        Truth.assertThat(chat.groupId).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of targetChatId to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToTargetChatId() {
        val expected = "abc"
        chat = Chat(targetChatId = expected)
        Truth.assertThat(chat.targetChatId).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of targetChatId to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToTargetChatId() {
        val expected = "abc"
        val actual = "xyz"
        chat = Chat(targetChatId = expected)
        Truth.assertThat(chat.targetChatId).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of targetUserId to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToTargetUserId() {
        val expected = "abc"
        chat = Chat(targetUserId = expected)
        Truth.assertThat(chat.targetUserId).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of targetUserId to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToTargetUserId() {
        val expected = "abc"
        val actual = "xyz"
        chat = Chat(targetUserId = expected)
        Truth.assertThat(chat.targetUserId).isNotEqualTo(actual)
    }
}