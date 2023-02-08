package team.standalone.core.data.util.test

import team.standalone.core.data.domain.model.Birthdate
import team.standalone.core.data.domain.model.param.BirthdateParam

open class BirthdateTestUtil {

    val expectedDay: Int = 28
    val expectedMonth: Int = 1
    val expectedYear: Int = 1995

    fun getTestBirthdate(): Birthdate {
        return Birthdate(
            day = expectedDay,
            month = expectedMonth,
            year = expectedYear
        )
    }

    fun getTestBirthdateParam(): BirthdateParam {
        return BirthdateParam(
            day = expectedDay,
            month = expectedMonth,
            year = expectedYear
        )
    }
}