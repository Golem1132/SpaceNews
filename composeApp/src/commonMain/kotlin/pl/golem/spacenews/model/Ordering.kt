package pl.golem.spacenews.model

import org.jetbrains.compose.resources.StringResource
import spacenews.composeapp.generated.resources.Res
import spacenews.composeapp.generated.resources.published_at
import spacenews.composeapp.generated.resources.published_at_desc
import spacenews.composeapp.generated.resources.updated_at
import spacenews.composeapp.generated.resources.updated_at_desc

sealed class Ordering(val key: String, val value: StringResource) {
    data object PublishedAt: Ordering("published_at", Res.string.published_at)
    data object PublishedAtDesc: Ordering("-published_at", Res.string.published_at_desc)
    data object UpdatedAt: Ordering("updated_at", Res.string.updated_at)
    data object UpdatedAtDesc: Ordering("-updated_at", Res.string.updated_at_desc)
}