package data.remote.postsExample

import data.remote.HttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class PostsServiceImplementation(private val client: HttpClient): PostsService
 {
    override suspend fun getPosts(): List<PostResponse> {
        return try {
            client.get {url(HttpRoutes.POSTS)}.body()
        } catch(e: Exception){
            println("Error: ${e.message}")
            emptyList()
        }
    }

    override suspend fun createPosts(postRequest: PostRequest): PostResponse? {
        return try {
            client.post {
                url(HttpRoutes.POSTS)
                contentType(ContentType.Application.Json)
                setBody(postRequest)
            }.body()
        } catch(e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }
}