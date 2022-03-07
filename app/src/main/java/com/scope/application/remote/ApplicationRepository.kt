package com.scope.application.remote

import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers

class ApplicationRepository(private val api: ApplicationApi) {


    suspend fun requestListOfDrivers():ListDrivers{

        return api.requestListOfDrivers()

    }


    suspend fun requestDriverDetails(userID:String):GeoVehicles{

        return api.requestDriverDetails(userID)

    }
}
