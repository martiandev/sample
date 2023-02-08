package team.standalone.core.network.retrofit.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant

internal class InstantAdapter {
    @ToJson
    fun toJson(value: Instant?): String? {
        return value?.toString()
    }

    @FromJson
    fun fromJson(value: String?): Instant? {
        return value?.let {
            try {
                it.toInstant()
            } catch (e: Exception) {
                null
            }
        }
    }
}