package pl.golem.spacenews.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Launch(
    @SerialName("launch_id")
    val launchId: String?,
    val provider: String?
)
