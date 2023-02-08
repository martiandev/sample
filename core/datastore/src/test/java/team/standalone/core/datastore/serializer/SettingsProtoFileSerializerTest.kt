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
class SettingsProtoFileSerializerTest {

    private lateinit var settingsProtoFileSerializer: SettingsProtoFileSerializer

    private lateinit var inputStream: InputStream

    @Mock
    private lateinit var outputStream: OutputStream

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        settingsProtoFileSerializer = SettingsProtoFileSerializer()
        inputStream = ByteArrayInputStream("".toByteArray())
    }

    /**
     * Test case: should be able to receive a settings proto file if existing.
     * @assertions:
     *       assertThat: result is not null.
     * */
    @Test
    fun shouldBeAbleToReceiveASettingsProtoFile() = runTest {
        val result = settingsProtoFileSerializer.readFrom(inputStream)
        Truth.assertThat(result).isNotNull()
    }

    /**
     * Test case: should not be able to receive a settings proto file if readFrom failed.
     * @assertions:
     *       assertThat: should execute CorruptionException.
     * */
    @Test
    fun shouldNotBeAbleToReceiveASettingsProtoFile() = runTest {
        inputStream = ByteArrayInputStream("data-test".toByteArray())
        try {
            settingsProtoFileSerializer.readFrom(inputStream)
        } catch(exception: CorruptionException) {
            Truth.assertThat(exception.message).contains("Cannot read proto")
        }
    }

    /**
     * Test case: should be able to write from a settings proto.
     * @assertions:
     *       assertThat: result is equal to Unit.
     * */
    @Test
    fun shouldBeAbleToWriteFromOutputStream() = runTest {
        val settingsProtoFile = settingsProtoFileSerializer.readFrom(inputStream)
        val result = settingsProtoFileSerializer.writeTo(settingsProtoFile, outputStream)
        Truth.assertThat(result).isEqualTo(Unit)
    }
}