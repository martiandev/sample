package team.standalone.core.data.domain.model

import kotlinx.datetime.Instant
import team.standalone.core.common.util.ConstantUtil
import java.text.SimpleDateFormat
import java.util.*

data class Subscription(
    val subscribed: Boolean,
    val paused: Boolean,
    val firstBillingDate: Instant?,
    val startDate: Instant?,
    val expireDate: Instant?,
) {

    fun displayFirstBillingDate(): String? = firstBillingDate?.let {
        val format = SimpleDateFormat(ConstantUtil.DATE_FORMAT_SHORT_DATE, Locale.getDefault())
        format.format(Date(it.toEpochMilliseconds()))
    }

    fun displayExpiredDate(): String? = expireDate?.let {
        val format = SimpleDateFormat(ConstantUtil.DATE_FORMAT_SHORT_DATE, Locale.getDefault())
        format.format(Date(it.toEpochMilliseconds()))
    }

    fun hasFirstSubscription(): Boolean = firstBillingDate != null
}
