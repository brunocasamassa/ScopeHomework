package com.scope.application.remote

import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers
import retrofit2.http.GET
 import retrofit2.http.Path
import retrofit2.http.Query

interface ApplicationApi
{
    @GET("/api/?op=list")
    suspend fun requestListOfDrivers(
    ): ListDrivers


    @GET("/api/?op=getlocations")
    suspend fun requestDriverDetails(
        @Query("userid") route: String,
    ): GeoVehicles

}