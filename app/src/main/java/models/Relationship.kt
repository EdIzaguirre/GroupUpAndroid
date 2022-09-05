package models

class Relationship (
    val id: Int?,
    val user: User,
    val group: Group,
    val status: Status
) {
    enum class Status(val relationshipNumber: Int) {
        accepted(1),
        admin(2),
        owner(3)
    }
}
