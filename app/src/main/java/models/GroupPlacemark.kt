package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GroupPlacemark (
    @SerialName("location_name")
    var locationName: String,
    @SerialName("street_number")
    var streetNumber: String?,
    var street: String?,
    var city: String?,
    var state: String?,
    @SerialName("zip_code")
    var zipCode: String?,
    var country: String?
)

fun placemarkToString(placemark: GroupPlacemark): String {
    var result = String()
    result += placemark.locationName

    if (placemark.streetNumber !=  null  && placemark.street !=  null) {
        result += "/n" + placemark.streetNumber + placemark.street
    }

    if (placemark.city !=  null  && placemark.state !=  null && placemark.zipCode !=  null) {
        result += "/n" + placemark.city + placemark.state + placemark.zipCode
    }

    if (placemark.country !=  null) {
        result += "/n" + placemark.country
    }

    return result
}