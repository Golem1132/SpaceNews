package pl.golem.spacenews.screen.articles

import app.cash.sqldelight.Query
import pl.golem.spacenews.RemoteKeys
import pl.golem.spacenews.RemoteKeysQueries
import pl.golem.spacenews.Result
import pl.golem.spacenews.ResultQueries
import pl.golem.spacenews.api.ArticlesService
import pl.golem.spacenews.api.Request
import pl.golem.spacenews.data.remote.Author
import pl.golem.spacenews.data.remote.Event
import pl.golem.spacenews.data.remote.Launch
import pl.golem.spacenews.data.remote.SpaceNewsResult

class ArticlesRepository(
    private val resultQueries: ResultQueries,
    private val keysQueries: RemoteKeysQueries,
    private val articlesService: ArticlesService
) {
    suspend fun fetch(offset: Int): Request<Int, String, SpaceNewsResult> {
        return articlesService.getArticles(offset)
    }

    suspend fun insertNewArticles(result: pl.golem.spacenews.data.remote.Result, offset: Long) {
        resultQueries.insertResult(
            result.id.toLong(),
            result.title,
            result.authors,
            result.url,
            result.imageUrl,
            result.newsSite,
            result.summary,
            result.publishedAt,
            result.updatedAt,
            result.featured,
            result.launches,
            result.events,
            offset
        )
    }

    suspend fun insertNewKey(keys: RemoteKeys) {
        keysQueries.addNewKeys(keys)
    }

    suspend fun deleteOldResults() {
        resultQueries.clearTable()
    }

    suspend fun deleteOldKeys() {
        keysQueries.deleteKeys()
    }


    fun getResult(offset: Long): List<pl.golem.spacenews.data.remote.Result> =
        resultQueries.getAllOffset(offset, mapper = {
                id: Long, title: String?, authors: List<Author>?, url: String?, imageUrl: String?, newsSite: String?, summary: String?, publishedAt: String?, updatedAt: String?, featured: Boolean?, launches: List<Launch>?, events: List<Event>?, _ ->
            pl.golem.spacenews.data.remote.Result(id.toInt(), title ?: "", authors, url, imageUrl, newsSite, summary, publishedAt, updatedAt, featured ?: false , launches, events)
        }).executeAsList()

}