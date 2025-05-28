package pl.golem.spacenews.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val name: String?,
    val socials: Social?
)
