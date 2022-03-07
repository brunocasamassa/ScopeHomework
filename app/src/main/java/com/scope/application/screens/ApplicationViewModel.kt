package com.scope.application.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scope.application.domain.models.Driver
import com.scope.application.domain.models.GeoAuto
import com.scope.application.remote.ApplicationUseCase
import com.scope.application.utils.SafeResponse
import com.scope.application.utils.getOrSafe
import com.scope.application.utils.safeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ApplicationViewModel(
    val usecase: ApplicationUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.Main + SupervisorJob()
) : ViewModel() {

    val applicationLiveData = MutableLiveData<ViewModelCommands>()

    fun getListOfDrivers() {
        viewModelScope.launch(dispatcher) {
            when (val response = safeRequest { usecase.getList() }) {
                is SafeResponse.Success -> {
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
        viewModelScope.launch(dispatcher) {
            when (val response = safeRequest { usecase.getVehiclesGeo(userId) }) {
                is SafeResponse.Success -> {
                    applicationLiveData.value =
                        ViewModelCommands.OnGeoVehiclesRequested(response.value.geoAuto)
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
}

sealed class ViewModelCommands {
    data class OnListOfDriversRequested(val drivers: List<Driver>) : ViewModelCommands()
    data class OnGeoVehiclesRequested(val vehicles: List<GeoAuto>) : ViewModelCommands()
    data class OnError(val message: String) : ViewModelCommands()
}