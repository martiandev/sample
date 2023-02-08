package team.standalone.core.data.domain.model

import kotlinx.datetime.Instant

data class Artist (
    val uid: String,
    val chatColor: String,
    val icon: String,
    val nickName: String,
    val createdAt: Instant?,
    val updatedAt: Instant?,
)

