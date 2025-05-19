package pl.golem.spacenews.data

import kotlinx.serialization.Serializable

@Serializable
data class SpaceNewsResult(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<Result>
)
