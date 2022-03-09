package com.scope.application.screens

import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.scope.application.domain.vo.CarPointVO
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment : Fragment() {

    val viewModel by viewModel<ApplicationViewModel>()

    lateinit var pointSelected: CarPointVO

    lateinit var fusedLocationClient : FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

            }
            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
            } else -> {
            // No location access granted.
        }
        }
    }
}
