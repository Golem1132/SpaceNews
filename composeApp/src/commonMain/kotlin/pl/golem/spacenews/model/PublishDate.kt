package pl.golem.spacenews.model

import kotlinx.datetime.LocalDateTime

data class PublishDate(
    val from: LocalDateTime?,
    val to: LocalDateTime?
)
