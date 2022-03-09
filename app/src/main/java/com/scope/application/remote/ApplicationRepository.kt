package com.scope.application.remote

import com.google.android.gms.maps.model.LatLng
import com.scope.application.domain.models.GeoContent
import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers

class ApplicationRepository(private val api: ApplicationApi) {

    suspend fun requestListOfDrivers():ListDrivers{
        return api.requestListOfDrivers()
    }

    suspend fun requestPointsDirections(originPoint:String,destinationPoint:String):GeoContent{
        return api.requestPointsDirections(originPoint,destinationPoint)
    }

    suspend fun requestDriverDetails(userID:String):GeoVehicles{
        return api.requestDriverDetails(userID)
    }


}
