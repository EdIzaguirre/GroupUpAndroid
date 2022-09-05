package models

import java.net.URL

class User (
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String?,
    var passwordConfirmation: String?,
    val imageURL: URL?,
    val id: Int?,
    val relationshipsMap: Array<Relationship>?
        )