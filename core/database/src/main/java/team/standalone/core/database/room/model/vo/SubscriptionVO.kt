package team.standalone.core.database.room.model.vo

import androidx.room.ColumnInfo
import kotlinx.datetime.Instant

data class SubscriptionVO(
    @ColumnInfo(name = "subscribed") val subscribed: Boolean,
    @ColumnInfo(name = "paused") val paused: Boolean,
    @ColumnInfo(name = "first_billing_date") val firstBillingDate: Instant?,
    @ColumnInfo(name = "start_date") val startDate: Instant?,
    @ColumnInfo(name = "expire_date") val expireDate: Instant?,
)
