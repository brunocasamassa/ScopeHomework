package com.scope.application.remote

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.scope.application.domain.models.GeoContent
import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers

interface ApplicationUseCase {

    suspend fun getList(): ListDrivers
    suspend fun getGeoDirections(originPoint:LatLng, destinationPoint:LatLng): GeoContent
    suspend fun getVehiclesGeo(userId: String, context: Context): GeoVehicles

}