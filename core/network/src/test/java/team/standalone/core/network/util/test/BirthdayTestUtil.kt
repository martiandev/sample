package team.standalone.core.network.util.test

import team.standalone.core.network.model.request.BirthdateRequest

open class BirthdayTestUtil {

    val expectedDay: Int = 28
    val expectedMonth: Int = 1
    val expectedYear: Int = 1995

    fun getTestBirthdateRequest(): BirthdateRequest {
        return BirthdateRequest(
            day = expectedDay,
            month = expectedMonth,
            year = expectedYear
        )
    }
}