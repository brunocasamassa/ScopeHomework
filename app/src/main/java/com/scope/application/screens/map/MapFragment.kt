package com.scope.application.screens.map

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.orhanobut.hawk.Hawk
import com.scope.application.R
import com.scope.application.databinding.FragmentMapBinding
import com.scope.application.domain.models.Driver
import com.scope.application.domain.models.GeoVehicles
import com.scope.application.domain.models.ListDrivers
import com.scope.application.domain.models.Vehicle
import com.scope.application.screens.ApplicationViewModel
import com.scope.application.screens.ApplicationViewModel.Companion.DRIVERS_LIST_KEY
import com.scope.application.screens.ApplicationViewModel.Companion.DRIVER_GEO_KEY
import com.scope.application.screens.BaseFragment
import com.scope.application.screens.ViewModelCommands
import com.scope.application.screens.list.ListFragment.Companion.USER_ID_SELECTED
import com.scope.application.screens.map.customviews.CarSelectedBottomDialog
import com.scope.application.utils.CustomCountdownTimer
import com.scope.application.utils.toDp
import com.scope.application.utils.toDpInt
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment(), OnMapReadyCallback {


    companion object {
        const val MAP_PADDING = 100
        const val REQUEST_MILLIS: Long = 5000
    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        //val userId = arguments?.getString(USER_ID_SELECTED)

        viewModel.getVehiclesGeo("3")

    }


    lateinit var binding: FragmentMapBinding
    lateinit var googleMap: GoogleMap

    private val viewModel by viewModel<ApplicationViewModel>()

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

        setupObserver()

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun setupObserver() {
        viewModel.applicationLiveData.observe(this.viewLifecycleOwner) { response ->
            when (response) {
                is ViewModelCommands.OnGeoVehiclesRequested -> {
                    val bounds = LatLngBounds.builder()

                    with(googleMap) {
                        clear()

                        setOnMarkerClickListener { marker ->

                            val driver = Hawk.get<ListDrivers>(DRIVERS_LIST_KEY).drivers.find {
                                it.userid == arguments?.getString(USER_ID_SELECTED)?.toInt()
                            }

                            val car = driver?.vehicles?.find { it.vehicleid == marker.title.removePrefix("Marker ").toInt() }

                            CarSelectedBottomDialog(car!!).show(childFragmentManager, "car_selected_tag")
                            true
                        }

                        response.vehicles.forEach {
                            val vehiclePosition = LatLng(it.lat, it.lon)
                            this.addMarker(
                                MarkerOptions()
                                    .position(vehiclePosition)
                                    .title("Marker ${it.vehicleid}")
                            )

                            bounds.include(vehiclePosition)

                        }

                        moveCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds.build(),
                                MAP_PADDING.toDpInt()
                            )
                        )

                    }


                    startRefreshCountdown()


                }

                is ViewModelCommands.OnError -> {
                    viewModel.getVehiclesGeo("3")
                }
            }
        }
    }

    private fun startRefreshCountdown() {
        with(CustomCountdownTimer(REQUEST_MILLIS)) {
            onTickCallback = { binding.timerTextView.text = it }
            onFinishCallback = { viewModel.getVehiclesGeo("2") }

            start()
        }
    }


}