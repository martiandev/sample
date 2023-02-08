package team.standalone.core.database.room.converter

import com.google.common.truth.Truth
import kotlinx.datetime.Instant
import org.junit.Test
import java.util.*


class InstantConverterTest{

    /**
     * Checks if converter can convert from Long to Instant
     */
    @Test()
    fun shouldBeAbleToConvertFromLongToInstant(){
        var date = Date()
        var instant = Instant.fromEpochMilliseconds(date.time)
        var result =  InstantConverter.convert(date.time)
        Truth.assertThat(result)
            .isAtLeast(instant)
    }

    /**
     * Checks if converter can convert from Instant to Long
     */
    @Test()
    fun shouldBeAbleToConvertFromInstantToLong(){
        var date = Date()
        var long = date.time
        var instant = Instant.fromEpochMilliseconds(date.time)
        var result =  InstantConverter.convert(instant)
        Truth.assertThat(result)
            .isAtLeast(long)
    }

}