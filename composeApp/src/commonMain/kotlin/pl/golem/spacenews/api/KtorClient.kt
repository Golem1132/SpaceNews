package pl.golem.spacenews.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class KtorClient {
    private val client = HttpClient() {
        expectSuccess = true
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000L
        }
        install(ContentEncoding) {
            gzip(1f)
            deflate(1f)
        }
    }

    val articlesService = ArticlesService(client)
    val reportsService = ReportsService(client)
    val blogsService = BlogsService(client)
}