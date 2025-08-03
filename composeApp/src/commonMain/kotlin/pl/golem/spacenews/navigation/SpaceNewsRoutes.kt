package pl.golem.spacenews.navigation

import kotlinx.serialization.Serializable

@Serializable
data class WebView(
    val url: String
)

@Serializable
data object Articles