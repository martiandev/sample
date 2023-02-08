package team.standalone.core.database.room.model.vo

import androidx.room.ColumnInfo

data class BirthdateVO(
    @ColumnInfo(name = "day") val day: Int,
    @ColumnInfo(name = "month") val month: Int,
    @ColumnInfo(name = "year") val year: Int
)
