package com.scope.application.remote

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.scope.application.domain.models.GeoContent
import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers
import java.util.*
import kotlin.coroutines.coroutineContext

class ApplicationUseCaseImpl(val repository: ApplicationRepository) : ApplicationUseCase {

    override suspend fun getList(): ListDrivers {
        return repository.requestListOfDrivers()

    }

    override suspend fun getGeoDirections(
        originPoint: LatLng,
        destinationPoint: LatLng
    ): GeoContent {

        return repository.requestPointsDirections("${originPoint.latitude},${originPoint.longitude}","${destinationPoint.latitude},${destinationPoint.longitude}")
    }

    override suspend fun getVehiclesGeo(userId:String, context:Context): GeoVehicles {
        var responseWithAddress = repository.requestDriverDetails(userId)

        responseWithAddress.geoAuto.forEach {
            val geo = Geocoder(context, Locale.getDefault())
            it.address = geo.getFromLocation(it.lat,it.lon,1)[0].getAddressLine(0)
        }

        return responseWithAddress
    }
}