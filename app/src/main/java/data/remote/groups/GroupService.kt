package data.remote.groups

import data.remote.postsExample.PostResponse
import models.Group

interface GroupService {

    suspend fun getGroups(): List<Group>


}