package team.standalone.core.database.room.model.vo

import androidx.room.ColumnInfo

data class AddressVO(
    @ColumnInfo(name = "structure") val structure: String, // buildings and room number
    @ColumnInfo(name = "number") val number: String, // address
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "prefecture") val prefecture: String,
    @ColumnInfo(name = "postal_code") val postalCode: String
)