package com.scope.application.domain.vo

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.model.BitmapDescriptor
import com.scope.application.R
import com.scope.application.domain.models.GeoAuto
import com.scope.application.domain.models.Vehicle
import com.scope.commons.BitmapHelper

data class CarPointVO(
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val vehicleId: String?,
    val color: String?,
    val photo: String?,
    val model: String?,
    val make: String?,
    val icon: BitmapDescriptor
) {





    companion object {
        fun create(vehicle: Vehicle?, geoAuto: GeoAuto, context: Context): CarPointVO {
            return CarPointVO(
                geoAuto.lat,
                geoAuto.lon,
                geoAuto.address,
                vehicle?.vehicleid.toString(),
                vehicle?.color,
                vehicle?.foto,
                vehicle?.model,
                vehicle?.make,
                icon = com.scope.commons.BitmapHelper.vectorToBitmap(context, R.drawable.ic_car, Color.parseColor(vehicle?.color ?: "#000000"))
            )
        }
    }
}