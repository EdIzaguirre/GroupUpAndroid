package models

import com.google.android.gms.maps.model.LatLng
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.net.URL

@Serializable
data class Group (
    @SerialName("name")
    var name: String,
    @SerialName("category")
    var category: Int,
    @SerialName("lat")
    var latitude: Double,
    @SerialName("lon")
    var longitude: Double,
    @SerialName("location_name")
    var location_name: String?,
    @SerialName("street_number")
    var street_number: String?,
    @SerialName("street")
    var street: String?,
    @SerialName("city")
    var city: String?,
    @SerialName("state")
    var state: String?,
    @SerialName("zip_code")
    var zip_code: String?,
    @SerialName("country")
    var country: String?,
    @SerialName("description")
    var description: String?,
    @SerialName("image_url")
    @Serializable(with = LinkSerializer::class)
    var image_url: URL?,
    @SerialName("url")
    var url: String?,
    @SerialName("id")
    var id: Int?
)

object LinkSerializer : KSerializer<URL?> {
    override val descriptor = PrimitiveSerialDescriptor("URL", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: URL?) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): URL? {
        return URL(decoder.decodeString())
    }
}

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