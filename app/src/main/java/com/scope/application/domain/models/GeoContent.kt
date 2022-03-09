package com.scope.application.domain.models

import android.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class GeoContent(
    @SerializedName("geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypoint>,
    @SerializedName("routes")
    val routes: List<Route>,
    @SerializedName("status")
    val status: String
) {

    companion object {


        fun createPolylineOptions(steps: List<Step>): PolylineOptions {
            var polylineOptions = PolylineOptions()

            steps.forEach { step ->
                polylineOptions.add(LatLng(step.startLocation.lat, step.startLocation.lng))
            }

            with(polylineOptions) {
                width(8f)
                color(Color.RED)
                geodesic(true)
            }


            return polylineOptions

        }
    }
}

@Serializable
data class GeocodedWaypoint(
    @SerializedName("geocoder_status")
    val geocoderStatus: String,
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("types")
    val types: List<String>
)

@Serializable
data class Route(
    @SerializedName("bounds")
    val bounds: Bounds,
    @SerializedName("copyrights")
    val copyrights: String,
    @SerializedName("legs")
    val legs: List<Leg>,
    @SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("warnings")
    val warnings: List<Any>,
    @SerializedName("waypoint_order")
    val waypointOrder: List<Any>
)

@Serializable
data class Bounds(
    @SerializedName("northeast")
    val northeast: Northeast,
    @SerializedName("southwest")
    val southwest: Southwest
)

@Serializable
data class Leg(
    @SerializedName("distance")
    val distance: Distance,
    @SerializedName("duration")
    val duration: Duration,
    @SerializedName("end_address")
    val endAddress: String,
    @SerializedName("end_location")
    val endLocation: EndLocation,
    @SerializedName("start_address")
    val startAddress: String,
    @SerializedName("start_location")
    val startLocation: StartLocation,
    @SerializedName("steps")
    val steps: List<Step>,
    @SerializedName("traffic_speed_entry")
    val trafficSpeedEntry: List<Any>,
    @SerializedName("via_waypoint")
    val viaWaypoint: List<Any>
)

@Serializable
data class OverviewPolyline(
    @SerializedName("points")
    val points: String
)

@Serializable
data class Northeast(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

@Serializable
data class Southwest(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

@Serializable
data class Distance(
    @SerializedName("text")
    val text: String,
    @SerializedName("value")
    val value: Int
)

@Serializable
data class Duration(
    @SerializedName("text")
    val text: String,
    @SerializedName("value")
    val value: Int
)

@Serializable
data class EndLocation(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

@Serializable
data class StartLocation(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

@Serializable
data class Step(
    @SerializedName("distance")
    val distance: DistanceX,
    @SerializedName("duration")
    val duration: DurationX,
    @SerializedName("end_location")
    val endLocation: EndLocationX,
    @SerializedName("html_instructions")
    val htmlInstructions: String,
    @SerializedName("maneuver")
    val maneuver: String,
    @SerializedName("polyline")
    val polyline: Polyline,
    @SerializedName("start_location")
    val startLocation: StartLocationX,
    @SerializedName("travel_mode")
    val travelMode: String
)

@Serializable
data class DistanceX(
    @SerializedName("text")
    val text: String,
    @SerializedName("value")
    val value: Int
)

@Serializable
data class DurationX(
    @SerializedName("text")
    val text: String,
    @SerializedName("value")
    val value: Int
)

@Serializable
data class EndLocationX(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

@Serializable
data class Polyline(
    @SerializedName("points")
    val points: String
)

@Serializable
data class StartLocationX(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

