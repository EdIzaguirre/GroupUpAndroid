package models

import com.google.android.gms.maps.model.LatLng
import io.ktor.http.*
import java.net.URL

data class Group (
    var name: String,
    var category: Categories,
    var location: LatLng,
    var groupPlacemark: GroupPlacemark,
    var description: String?,
    var members: Int?,
    var imageURL: URL?,
    var id: Int?
)

enum class Categories (val categoryNumber: Int) {
    academic(1),
    arts(2),
    club_sports(3),
    community_service(4),
    cultural(5),
    educational(6),
    health_and_wellness(7),
    hobbies(8),
    political(9),
    religous(10),
    social(11),
    other(12);
}

val stockPhotoURLs = arrayOf(
    URL("https://thumbs.dreamstime.com/b/startup-business-people-teamwork-cooperation-hands-together-92936470.jpg"),
    URL("https://st.depositphotos.com/1743476/1276/i/950/depositphotos_12766794-stock-photo-teamwork-and-cooperation.jpg"),
    URL("https://previews.123rf.com/images/fotokostic/fotokostic1610/fotokostic161000068/64217984-kids-soccer-team-in-group-huddle.jpg"),
    URL("https://media.istockphoto.com/photos/group-of-friends-playing-basketball-picture-id597222210"),
    URL("https://media.istockphoto.com/photos/happy-community-service-people-cleaning-up-the-park-picture-id578274632"),
    URL("https://images.fineartamerica.com/images/artworkimages/mediumlarge/1/gentlemen-in-the-tavern-max-gaisser.jpg"),
    URL("https://afktravel.com/wp-content/uploads/2014/07/business-meeting-africa.jpg"),
    URL("https://s3.amazonaws.com/StartupStockPhotos/20140808_StartupStockPhotos/58.jpg"),
    URL("https://media.istockphoto.com/photos/people-attending-bible-study-or-book-group-meeting-in-community-picture-id1145051385?k=6&m=1145051385&s=612x612&w=0&h=OoKnmHexs28UIw1o13wumrvHeHQVvdy8o7z8Rt1IzRg=")
)