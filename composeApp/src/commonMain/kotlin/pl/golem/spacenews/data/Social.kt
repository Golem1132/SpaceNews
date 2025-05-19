package pl.golem.spacenews.data

import kotlinx.serialization.Serializable

@Serializable
data class Social(
    val x: String?,
    val youtube: String?,
    val instagram: String?,
    val linkedin: String?,
    val mastodon: String?,
    val bluesky: String?
)
