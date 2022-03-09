package com.scope.application.remote

import com.google.android.gms.maps.model.LatLng
import com.scope.application.domain.models.GeoContent
import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers
import retrofit2.http.*

interface ApplicationApi
{
    @GET("/api/?op=list")
    suspend fun requestListOfDrivers(
    ): ListDrivers


    @GET("/api/?op=getlocations")
    suspend fun requestDriverDetails(
        @Query("userid") route: String,
    ): GeoVehicles



    @GET("https://maps.googleapis.com/maps/api/directions/json")
    suspend fun requestPointsDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String= "driving",
        @Query("key") key: String = com.scope.application.BuildConfig.MAPS_API_KEY,
    ): GeoContent

}