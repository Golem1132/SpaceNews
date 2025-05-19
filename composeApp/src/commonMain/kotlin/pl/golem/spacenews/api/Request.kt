package pl.golem.spacenews.api

sealed interface Request<Int, String, T> {
    data class Success<T>(val data: T) : Request<Int, String, T>
    data class Failure<T>(
        val statusCode: Int,
        val description: String
    ) : Request<Int, String, T>
}