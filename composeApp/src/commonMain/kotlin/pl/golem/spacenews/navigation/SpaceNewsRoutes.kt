package pl.golem.spacenews.navigation

import kotlinx.serialization.Serializable

@Serializable
data class WebScreen(
    val url: String
)


@Serializable
data object Main

@Serializable
data object Articles

@Serializable
data object Blogs

@Serializable
data object Reports
