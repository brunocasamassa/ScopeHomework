package com.scope.application.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.scope.application.R
import com.scope.application.databinding.FragmentMapBinding
import com.scope.application.domain.vo.CarPointVO
import com.scope.application.screens.BaseFragment
import com.scope.application.screens.ViewModelCommands
import com.scope.application.screens.ViewModelCommands.OnError
import com.scope.application.screens.ViewModelCommands.OnGeoVehiclesRequested
import com.scope.application.screens.ViewModelCommands.OnDirectionsRequested
import com.scope.application.screens.list.ListFragment.Companion.USER_ID_SELECTED
import com.scope.application.screens.map.customviews.CarSelectedBottomDialog
import com.scope.application.utils.CustomCountdownTimer
import com.scope.application.utils.toDpInt


class MapFragment : BaseFragment(), OnMapReadyCallback {


    companion object {
        const val MAP_PADDING = 100
        const val REQUEST_MILLIS: Long = 60000
    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0


        viewModel.getVehiclesGeo(userId)

    }


    lateinit var binding: FragmentMapBinding
    lateinit var googleMap: GoogleMap

    lateinit var userId: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getString(USER_ID_SELECTED) ?: ""

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        setupObserver()

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    private fun setupObserver() {
        viewModel.applicationLiveData.observe(this.viewLifecycleOwner) { response ->
            when (response) {
                is OnGeoVehiclesRequested -> {

                    setupMap(response.carPoints)

                    startRefreshCountdown()


                }

                is OnDirectionsRequested -> {
                    googleMap.addPolyline(response.polylineOptions)

                    CarSelectedBottomDialog(response.carPointClicked).show(
                        childFragmentManager,
                        "car_selected_tag"
                    )

                }

                is OnError -> {
                    viewModel.getVehiclesGeo(userId)
                }
            }
        }
    }

    private fun setupMap(carPoints: List<CarPointVO>) {
        val bounds = LatLngBounds.builder()

        with(googleMap) {
            clear()

             carPoints.forEach {
                val vehiclePosition = LatLng(it.latitude, it.longitude)
                this.addMarker(
                    MarkerOptions()
                        .icon(it.icon)
                        .position(vehiclePosition)
                        .title("${it.vehicleId}")
                )

                bounds.include(vehiclePosition)
            }

            setOnMarkerClickListener { marker ->
                 carPoints.find {
                     it.vehicleId == marker.title
                }?.let {carPoint ->

                    pointSelected = carPoint

                     locationPermissionRequest.launch(arrayOf(
                         Manifest.permission.ACCESS_FINE_LOCATION,
                         Manifest.permission.ACCESS_COARSE_LOCATION))


                }
                true
            }

            moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds.build(),
                    MAP_PADDING.toDpInt()
                )
            )

        }


    }

    private fun startRefreshCountdown() {
        with(CustomCountdownTimer(REQUEST_MILLIS)) {
            onTickCallback = {
                binding.timerTextView.text = it
                if (!this@MapFragment.isVisible) this.cancel()
            }
            onFinishCallback = { viewModel.getVehiclesGeo(userId) }

            start()

        }
    }



    }

