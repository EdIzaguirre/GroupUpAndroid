package data.remote

import io.ktor.client.statement.*

interface PostsService {

    suspend fun getPosts(): List<PostResponse>

    suspend fun createPosts(postRequest: PostRequest): HttpResponse?
}
