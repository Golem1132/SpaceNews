package pl.golem.spacenews.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.io.IOException
import pl.golem.spacenews.data.Info
import pl.golem.spacenews.data.SpaceNewsResult

class ArticlesService(private val client: HttpClient) {

    suspend fun getArticles(
        publishedFrom: String? = null,
        publishedTo: String? = null,
        search: String = "",
        ordering: String? = null
    ): Request<Int, String, SpaceNewsResult> {
        return try {
            Request.Success(client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.spaceflightnewsapi.net"
                    path("v4", "articles")
                    parameters.apply {
                        if (ordering != null)
                            append("ordering", ordering)
                        if (search.isNotBlank())
                            append("search", search)
                        if (publishedFrom != null)
                            append("published_at_gte", publishedFrom)
                        if (publishedTo != null)
                            append("published_at_lte", publishedTo)

                    }
                }
            }.body())
        } catch (e: ClientRequestException) {
            Request.Failure(e.response.status.value, e.message)
        } catch (e: ServerResponseException) {
            Request.Failure(e.response.status.value, e.message)
        } catch (e: IOException) {
            Request.Failure(0, e.message ?: "")
        } catch (e: IllegalStateException) {
            Request.Failure(0, e.message ?: "")
        }
    }

    /*    suspend fun getReports(): SpaceNewsResult {
            return try {
                client.get("https://api.spaceflightnewsapi.net/v4/reports/?format=json")
                    .body<SpaceNewsResult>()
            } catch (e: Exception) {
                e.printStackTrace()
                SpaceNewsResult(
                    0,
                    "",
                    "",
                    emptyList()
                )
            }
        }*/

    /*    suspend fun getBlogs(): SpaceNewsResult {
            return try {
                client.get("https://api.spaceflightnewsapi.net/v4/blogs/?format=json")
                    .body<SpaceNewsResult>()
            } catch (e: Exception) {
                e.printStackTrace()
                SpaceNewsResult(
                    0,
                    "",
                    "",
                    emptyList()
                )
            }
        }*/

    suspend fun getInfo(): Info {
        return try {
            client.get("https://api.spaceflightnewsapi.net/v4/info/?format=json").body<Info>()
        } catch (e: Exception) {
            e.printStackTrace()
            Info(
                "",
                emptyList()
            )
        }
    }
}