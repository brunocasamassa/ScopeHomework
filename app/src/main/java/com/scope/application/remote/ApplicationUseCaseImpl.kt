package com.scope.application.remote

import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers

class ApplicationUseCaseImpl(val repository: ApplicationRepository) : ApplicationUseCase {

    override suspend fun getList(): ListDrivers {
        return repository.requestListOfDrivers()

    }

    override suspend fun getVehiclesGeo(userId:String): GeoVehicles {
        return repository.requestDriverDetails(userId)
    }
}