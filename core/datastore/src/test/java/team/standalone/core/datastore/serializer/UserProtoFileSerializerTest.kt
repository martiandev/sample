package team.standalone.core.datastore.serializer

import androidx.datastore.core.CorruptionException
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalCoroutinesApi::class)
class UserProtoFileSerializerTest {

    private lateinit var userProtoFileSerializer: UserProtoFileSerializer

    private lateinit var inputStream: InputStream

    @Mock
    private lateinit var outputStream: OutputStream

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userProtoFileSerializer = UserProtoFileSerializer()
        inputStream = ByteArrayInputStream("".toByteArray())
    }

    /**
     * Test case: should be able to receive a user proto file if existing.
     * @assertions:
     *       assertThat: result is not null.
     * */
    @Test
    fun shouldBeAbleToReceiveAUserProtoFile() = runTest {
        val result = userProtoFileSerializer.readFrom(inputStream)
        Truth.assertThat(result).isNotNull()
    }

    /**
     * Test case: should not be able to receive a user proto file if readFrom failed.
     * @assertions:
     *       assertThat: should execute CorruptionException.
     * */
    @Test
    fun shouldNotBeAbleToReceiveAUserProtoFile() = runTest {
        inputStream = ByteArrayInputStream("data-test".toByteArray())
        try {
            userProtoFileSerializer.readFrom(inputStream)
        } catch(exception: CorruptionException) {
            Truth.assertThat(exception.message).contains("Cannot read proto")
        }
    }

    /**
     * Test case: should be able to write from a user proto.
     * @assertions:
     *       assertThat: result is equal to Unit.
     * */
    @Test
    fun shouldBeAbleToWriteFromOutputStream() = runTest {
        val settingsProtoFile = userProtoFileSerializer.readFrom(inputStream)
        val result = userProtoFileSerializer.writeTo(settingsProtoFile, outputStream)
        Truth.assertThat(result).isEqualTo(Unit)
    }
}