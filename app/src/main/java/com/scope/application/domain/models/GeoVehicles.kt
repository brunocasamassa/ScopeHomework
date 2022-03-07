package com.scope.application.domain.models
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class GeoVehicles(
    @SerialName("data")
    val geoAuto: List<GeoAuto>
)

@Serializable
data class GeoAuto(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("vehicleid")
    val vehicleid: Int
)
