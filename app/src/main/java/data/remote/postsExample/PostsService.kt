package data.remote.postsExample

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface PostsService {

    suspend fun getPosts(): List<PostResponse>

    suspend fun createPosts(postRequest: PostRequest): PostResponse?

    companion object {
        fun create(): PostsService {
            return PostsServiceImplementation (
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.NONE
                    }

                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                        })
                    }
                }
            )
        }
    }
}
