package com.scope.application.screens

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.orhanobut.hawk.Hawk
import com.scope.application.domain.models.*
import com.scope.application.domain.vo.CarPointVO
import com.scope.application.remote.ApplicationUseCase
import com.scope.commons.CustomCountdownTimer
import com.scope.application.http.SafeResponse
import com.scope.application.http.safeRequest
import com.scope.commons.extensions.getOrSafe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class ApplicationViewModel(
    private val context: Context,
    private val usecase: ApplicationUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.Main + SupervisorJob()
) : ViewModel() {

    val applicationLiveData = MutableLiveData<ViewModelCommands>()

    fun getListOfDrivers() {
        viewModelScope.launch(dispatcher) {
            when (val response = safeRequest { usecase.getList() }) {
                is SafeResponse.Success -> {
                    Hawk.put(DRIVERS_LIST_KEY, response.value)
                    applicationLiveData.value =
                        ViewModelCommands.OnListOfDriversRequested(response.value.drivers)
                }
                is SafeResponse.GenericError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.error?.message().getOrSafe())
                }
                is SafeResponse.NetworkError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.errorMessage.getOrSafe())

                }
            }
        }
    }


    fun getVehiclesGeo(userId: String) {
        val currentlyDriverVehicles =
            Hawk.get<ListDrivers>(DRIVERS_LIST_KEY).drivers.find { it.userid.toString() == userId }?.vehicles

        //If has geo persisted, retrieve carPointsVO to UI Thread
        Hawk.get<List<GeoAuto>>(DRIVER_GEO_KEY(userId))?.let {
            applicationLiveData.value = ViewModelCommands.OnGeoVehiclesRequested(
                createListOfCarPoints(
                    currentlyDriverVehicles,
                    it
                )
            )
        }

        viewModelScope.async(dispatcher) {
            when (val response = safeRequest {
                usecase.getVehiclesGeo(
                    userId, Geocoder(
                        context,
                        Locale.getDefault()
                    )
                )
            }) {
                is SafeResponse.Success -> {

                    response.value.geoAuto.let {

                        /*persisting geo and set countdown to clean persistence*/
                        Hawk.put(DRIVER_GEO_KEY(userId), it)

                        with(com.scope.commons.CustomCountdownTimer(CLEAN_CACHE_TIME)) {
                            onFinishCallback = { Hawk.delete(DRIVER_GEO_KEY(userId)) }
                            start()
                        }

                        /**/

                        applicationLiveData.value = ViewModelCommands.OnGeoVehiclesRequested(
                            createListOfCarPoints(
                                currentlyDriverVehicles,
                                it
                            )
                        )
                    }
                }
                is SafeResponse.GenericError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.errorMessage.getOrSafe())
                }
                is SafeResponse.NetworkError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.errorMessage.getOrSafe())
                }

            }
        }
    }

    fun getPointDirections(carPointClicked: CarPointVO, userLocation: LatLng) {

        viewModelScope.launch(dispatcher) {
            when (val response = safeRequest {
                usecase.getGeoDirections(
                    originPoint = LatLng(
                        carPointClicked.latitude,
                        carPointClicked.longitude
                    ), destinationPoint = userLocation
                )
            }) {
                is SafeResponse.Success -> {
                    when (response.value.status) {
                        ZERO_RESULTS -> {
                            applicationLiveData.value = ViewModelCommands.OnDirectionsRequested(
                                null, carPointClicked
                            )
                        }
                        else -> {
                            response.value.routes.firstOrNull()?.legs?.firstOrNull()?.steps?.let {
                                applicationLiveData.value = ViewModelCommands.OnDirectionsRequested(
                                    GeoContent.createPolylineOptions(it), carPointClicked
                                )
                            }
                        }
                    }
                }
                is SafeResponse.GenericError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.error?.message().getOrSafe())
                }
                is SafeResponse.NetworkError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.errorMessage.getOrSafe())

                }
            }
        }

    }


    private fun createListOfCarPoints(
        currentlyDriverVehicles: List<Vehicle>?,
        it: List<GeoAuto>
    ): List<CarPointVO> {
        var listOfCarPoints = arrayListOf<CarPointVO>()
        it.forEach {
            listOfCarPoints.add(
                CarPointVO.create(
                    currentlyDriverVehicles?.find { vehicle -> vehicle.vehicleid == it.vehicleid },
                    it, context
                )
            )
        }

        return listOfCarPoints
    }


    companion object {
        fun DRIVER_GEO_KEY(currentlyUserId: String) = "_driver_geo_key_$currentlyUserId"
        const val DRIVERS_LIST_KEY = "_drivers_list_key"
        const val ZERO_RESULTS = "ZERO_RESULTS"
        const val CLEAN_CACHE_TIME: Long = 30000
    }
}

sealed class ViewModelCommands {
    data class OnListOfDriversRequested(val drivers: List<Driver>?) : ViewModelCommands()
    data class OnGeoVehiclesRequested(val carPoints: List<CarPointVO>) : ViewModelCommands()
    data class OnError(val message: String) : ViewModelCommands()
    data class OnDirectionsRequested(
        val polylineOptions: PolylineOptions?,
        val carPointClicked: CarPointVO
    ) : ViewModelCommands() {

    }
}