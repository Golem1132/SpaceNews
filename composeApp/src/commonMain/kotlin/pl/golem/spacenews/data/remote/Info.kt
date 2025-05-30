package pl.golem.spacenews.data.remote

import kotlinx.serialization.SerialName

data class Info(
    val version: String,
    @SerialName("news_sites")
    val newsSites: List<String>
)
