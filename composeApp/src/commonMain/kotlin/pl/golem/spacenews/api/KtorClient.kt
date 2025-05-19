package pl.golem.spacenews.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

class KtorClient {
    private val client = HttpClient() {
        expectSuccess = true
        install(ContentNegotiation) {
            json()
        }
    }

    val articlesService = ArticlesService(client)
}