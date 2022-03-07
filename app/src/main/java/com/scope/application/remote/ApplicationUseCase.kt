package com.scope.application.remote

import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers

interface ApplicationUseCase {

    suspend fun getList(): ListDrivers
    suspend fun getVehiclesGeo(userId: String): GeoVehicles

}