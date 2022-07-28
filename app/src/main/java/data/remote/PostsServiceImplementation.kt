package data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*

class PostsServiceImplementation(private val client: HttpClient): PostsService
 {
    override suspend fun getPosts(): List<PostResponse> {
        return client.get {url(HttpRoutes.POSTS) }.body()
    }

    override suspend fun createPosts(postRequest: PostRequest): HttpResponse? {
        return client.post {
            url(HttpRoutes.POSTS)
            contentType(ContentType.Application.Json)
            setBody(postRequest)
        }
    }

}