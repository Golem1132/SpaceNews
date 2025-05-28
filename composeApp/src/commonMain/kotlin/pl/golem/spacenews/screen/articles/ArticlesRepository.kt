package pl.golem.spacenews.screen.articles

import pl.golem.spacenews.ResultQueries
import pl.golem.spacenews.api.ArticlesService
import pl.golem.spacenews.api.Request
import pl.golem.spacenews.data.remote.Result

class ArticlesRepository(
    private val resultQueries: ResultQueries,
    private val articlesService: ArticlesService
) {
    suspend fun fetch() {
        with(articlesService.getArticles()) {
            if(this is Request.Success) {
                val data = this.data.results
                resultQueries.transaction {
                        data.forEach {
                            resultQueries.insertResult(
                                null,
                                it.title,
                                it.authors,
                                it.url,
                                it.imageUrl,
                                it.newsSite,
                                it.summary,
                                it.publishedAt,
                                it.updatedAt,
                                it.featured,
                                it.launches,
                                it.events
                            )
                        }
                }

            } else if (this is Request.Failure) {
                println(this.description)
            } else {
                println("ECH")
            }
        }
    }

    fun getArticles(): List<Result> {
        return resultQueries.getAll().executeAsList().map {
            Result(
                it.id.toInt(),
                it.title ?: "",
                it.authors,
                it.url,
                it.imageUrl,
                it.newsSite,
                it.summary,
                it.publishedAt,
                it.updatedAt,
                it.featured ?: false,
                it.launches,
                it.events
            )
        }
    }
}