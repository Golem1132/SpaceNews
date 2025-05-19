package pl.golem.spacenews.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("event_id")
    val eventId: Int?,
    val provider: String?
)
