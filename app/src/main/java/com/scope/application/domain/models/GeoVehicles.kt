package com.scope.application.domain.models
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class GeoVehicles(
    @SerializedName("data")
    val geoAuto: List<GeoAuto>
)

@Serializable
data class GeoAuto(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("vehicleid")
    val vehicleid: Int
)
