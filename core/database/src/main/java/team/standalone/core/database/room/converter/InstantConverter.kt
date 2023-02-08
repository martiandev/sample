package team.standalone.core.database.room.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

internal object InstantConverter {
    @TypeConverter
    fun convert(value: Long?): Instant? = value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun convert(instant: Instant?): Long? = instant?.toEpochMilliseconds()
}
