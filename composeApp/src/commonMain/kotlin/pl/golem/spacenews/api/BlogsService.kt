package pl.golem.spacenews.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.compression.compress
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.io.IOException
import pl.golem.spacenews.data.remote.SpaceNewsResult

class BlogsService(private val client: HttpClient)  {
    suspend fun getBlogs(
        offset: Int = 0,
        publishedFrom: String? = null,
        publishedTo: String? = null,
        search: String = "",
        ordering: String? = null
    ): Request<Int, String, SpaceNewsResult> {
        return try {

            Request.Success(client.get {
                compress("gzip")
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.spaceflightnewsapi.net"
                    path("v4", "blogs")
                    parameters.apply {
                        if (ordering != null)
                            append("ordering", ordering)
                        if (search.isNotBlank())
                            append("search", search)
                        if (publishedFrom != null)
                            append("published_at_gte", publishedFrom)
                        if (publishedTo != null)
                            append("published_at_lte", publishedTo)
                        append("offset", offset.toString())
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
}