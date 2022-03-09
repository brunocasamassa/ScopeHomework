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

    lateinit var fusedLocationClient : FusedLocationProviderClient

}
