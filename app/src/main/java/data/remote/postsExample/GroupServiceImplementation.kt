package data.remote.postsExample

import data.remote.HttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import models.Group
import java.lang.Exception

class GroupServiceImplementation(private val client: HttpClient): GroupService
 {
    override suspend fun getGroups(lat: String, lon: String, radius: String): List<Group> {
        return try {
            val startOfString = HttpRoutes.QUERY
            client.get {
                url("${startOfString}lat=${lat}&lon=${lon}&radius=${radius}")}.body()
        } catch(e: Exception){
            println("Error: ${e.message}")
            emptyList()
        }
    }

//    override suspend fun createPosts(postRequest: PostRequest): PostResponse? {
//        return try {
//            client.post {
//                url(HttpRoutes.QUERY)
//                contentType(ContentType.Application.Json)
//                setBody(postRequest)
//            }.body()
//        } catch(e: Exception) {
//            println("Error: ${e.message}")
//            null
//        }
//    }
}