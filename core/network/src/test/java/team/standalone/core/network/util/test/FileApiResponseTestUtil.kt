package team.standalone.core.network.util.test

import team.standalone.core.network.model.response.FileApiResponse

open class FileApiResponseTestUtil {

    val expectedStatus: Int = 1
    val expectedFileId: String = "123abc"

    fun getTestFileApiResponse(): FileApiResponse {
        return FileApiResponse(
            status = expectedStatus,
            fileId = expectedFileId
        )
    }
}