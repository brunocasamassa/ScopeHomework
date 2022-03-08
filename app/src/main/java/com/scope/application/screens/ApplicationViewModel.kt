package com.scope.application.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.hawk.Hawk
import com.scope.application.domain.models.Driver
import com.scope.application.domain.models.GeoAuto
import com.scope.application.remote.ApplicationUseCase
import com.scope.application.utils.CustomCountdownTimer
import com.scope.application.utils.SafeResponse
import com.scope.application.utils.getOrSafe
import com.scope.application.utils.safeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ApplicationViewModel(
    private val usecase: ApplicationUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.Main + SupervisorJob()
) : ViewModel() {

    val applicationLiveData = MutableLiveData<ViewModelCommands>()

    fun getListOfDrivers() {
        viewModelScope.launch(dispatcher) {
            when (val response = safeRequest { usecase.getList() }) {
                is SafeResponse.Success -> {
                    Hawk.put(DRIVERS_LIST_KEY,response.value)
                    applicationLiveData.value =
                        ViewModelCommands.OnListOfDriversRequested(response.value.drivers)
                }
                is SafeResponse.GenericError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.error?.message().getOrSafe())
                }
                is SafeResponse.NetworkError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.errorMessage?.getOrSafe())

                }

            }
        }
    }


    fun getVehiclesGeo(userId: String) {
        viewModelScope.async(dispatcher) {
            when (val response = safeRequest { usecase.getVehiclesGeo(userId) }) {
                is SafeResponse.Success -> {
                    response.value.geoAuto?.let {
                        Hawk.put(DRIVER_GEO_KEY(userId), it)

                        with(CustomCountdownTimer(CLEAN_CACHE_TIME)) {
                            onFinishCallback = { Hawk.delete(DRIVER_GEO_KEY(userId)) }
                            start()
                        }

                        applicationLiveData.value =
                            ViewModelCommands.OnGeoVehiclesRequested(it)
                    }
                }
                is SafeResponse.GenericError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.errorMessage?.getOrSafe())
                }
                is SafeResponse.NetworkError -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnError(response.errorMessage?.getOrSafe())

                }

            }
        }
    }

    companion object {
        fun DRIVER_GEO_KEY(currentlyUserId: String) = "_driver_geo_key_$currentlyUserId"
        const val DRIVERS_LIST_KEY = "_drivers_list_key"
        const val CLEAN_CACHE_TIME: Long = 30000
    }
}

sealed class ViewModelCommands {
    data class OnListOfDriversRequested(val drivers: List<Driver>?) : ViewModelCommands()
    data class OnGeoVehiclesRequested(val vehicles: List<GeoAuto>) : ViewModelCommands()
    data class OnError(val message: String) : ViewModelCommands()
}