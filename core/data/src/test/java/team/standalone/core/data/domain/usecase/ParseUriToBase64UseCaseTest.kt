package team.standalone.core.data.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.util.Base64
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.ByteArrayOutputStream

@ExperimentalCoroutinesApi
class ParseUriToBase64UseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var photoUrl: Uri

    @Mock
    private lateinit var source: ImageDecoder.Source

    @Mock
    private lateinit var bitmap: Bitmap

    @Mock
    private lateinit var matrix: Matrix

    @Mock
    private lateinit var byteArray: ByteArrayOutputStream

    private lateinit var mockedImageDecoder: MockedStatic<ImageDecoder>
    private lateinit var mockedBitmap: MockedStatic<Bitmap>
    private lateinit var mockedBase64: MockedStatic<Base64>

    private lateinit var parseUriToBase64UseCase: ParseUriToBase64UseCase
    private val photo = byteArrayOf()
    private val filter: Boolean = true
    private val toSize: Float = 20f
    private val size: Int = 0
    private val quality = 100

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        parseUriToBase64UseCase = ParseUriToBase64UseCase(Dispatchers.IO, context)
        mockedImageDecoder = Mockito.mockStatic(ImageDecoder::class.java)
        mockedBitmap = Mockito.mockStatic(Bitmap::class.java)
        mockedBase64 = Mockito.mockStatic(Base64::class.java)
        Mockito.`when`(ImageDecoder.createSource(context.contentResolver, photoUrl)).thenReturn(source)
    }

    @After
    fun teardown() {
        mockedImageDecoder.close()
        mockedBitmap.close()
        mockedBase64.close()
    }

    /**
     * Test case: should function be runnable.
     * */
    @Test
    fun shouldParseUriToBase64UseCase() = runTest {
        parseUriToBase64UseCase(photoUrl)
    }

    /**
     * Test case: should match the value of getCapturedPhoto to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToGetCapturedPhotoValue() {
        val expected = Mockito.mock(Bitmap::class.java)
        Mockito.`when`(ImageDecoder.decodeBitmap(source)).thenReturn(expected)
        val result = parseUriToBase64UseCase.getCapturedPhoto(photoUrl)
        assertThat(result).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of getCapturedPhoto to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToGetCapturedPhotoValue() {
        val expected = Mockito.mock(Bitmap::class.java)
        val actual = Mockito.mock(Bitmap::class.java)
        Mockito.`when`(ImageDecoder.decodeBitmap(source)).thenReturn(expected)
        val result = parseUriToBase64UseCase.getCapturedPhoto(photoUrl)
        assertThat(result).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of resizePhoto to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToResizePhotoValue() {
        val expected = Mockito.mock(Bitmap::class.java)
        Mockito.`when`(
            Bitmap.createBitmap(bitmap, size, size, bitmap.width, bitmap.height, matrix, filter)
        ).thenReturn(expected)
        val result = parseUriToBase64UseCase.resizePhoto(bitmap, toSize, matrix)
        assertThat(result).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of resizePhoto to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToResizePhotoValue() {
        val expected = Mockito.mock(Bitmap::class.java)
        val actual = Mockito.mock(Bitmap::class.java)
        Mockito.`when`(
            Bitmap.createBitmap(bitmap, size, size, bitmap.width, bitmap.height, matrix, filter)
        ).thenReturn(expected)
        val result = parseUriToBase64UseCase.resizePhoto(bitmap, toSize, matrix)
        assertThat(result).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of compressBitmap to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToCompressBitmapValue() {
        val expected = byteArrayOf()
        Mockito.`when`(byteArray.toByteArray()).thenReturn(expected)
        val result = parseUriToBase64UseCase.compressBitmap(bitmap, quality, byteArray)
        assertThat(result).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of compressBitmap to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToCompressBitmapValue() {
        val expected = byteArrayOf()
        val actual = byteArrayOf(1)
        Mockito.`when`(byteArray.toByteArray()).thenReturn(expected)
        val result = parseUriToBase64UseCase.compressBitmap(bitmap, quality, byteArray)
        assertThat(result).isNotEqualTo(actual)
    }

    /**
     * Test case: should match the value of byteArrayToBase64 to the expected value.
     * @assertions:
     *       assertThat: result is equal to expected value.
     * */
    @Test
    fun shouldBeEqualToByteArrayToBase64Value() {
        val expected = "abc"
        Mockito.`when`(Base64.encodeToString(photo, Base64.NO_WRAP)).thenReturn(expected)
        val result = parseUriToBase64UseCase.byteArrayToBase64(photo)
        assertThat(result).isEqualTo(expected)
    }

    /**
     * Test case: should not match the value of byteArrayToBase64 to the expected value.
     * @assertions:
     *       assertThat: result is not equal to expected value.
     * */
    @Test
    fun shouldNotBeEqualToByteArrayToBase64Value() {
        val expected = "abc"
        val actual = "xyz"
        Mockito.`when`(Base64.encodeToString(photo, Base64.NO_WRAP)).thenReturn(expected)
        val result = parseUriToBase64UseCase.byteArrayToBase64(photo)
        assertThat(result).isNotEqualTo(actual)
    }
}