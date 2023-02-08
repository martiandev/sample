package team.standalone.core.data.domain.usecase

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CopyTextUseCaseTest {

    @Mock
    private lateinit var context: Context

    private lateinit var mockedClipData: MockedStatic<ClipData>

    private lateinit var copyTextUseCase: CopyTextUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        copyTextUseCase = CopyTextUseCase(context)
        mockedClipData = Mockito.mockStatic(ClipData::class.java)
    }

    @After
    fun teardown() {
        mockedClipData.close()
    }

    /**
     * Test case: should copy string text to clipboard.
     * */
    @Test
    fun shouldCopyTextUseCase() {
        val expected = "Test text content"
        val clipboard = "CLIPBOARD"
        val clipboardManager = Mockito.mock(ClipboardManager::class.java)
        val clipData = Mockito.mock(ClipData::class.java)
        Mockito.`when`(context.getSystemService(Context.CLIPBOARD_SERVICE)).thenReturn(clipboardManager)
        Mockito.`when`(ClipData.newPlainText(clipboard, expected)).thenReturn(clipData)
        copyTextUseCase(expected)
    }
}