package com.scope.application.screens.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import com.scope.application.R
import com.scope.application.databinding.FragmentMapBinding
import com.scope.application.screens.BaseFragment


lateinit var binding: FragmentMapBinding

class MapFragment : BaseFragment() {

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

        with(binding){
            val mapFragment =  childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

            lifecycleScope.launchWhenCreated {
                // Get map
                val googleMap = mapFragment.awaitMap()

                /** setar marcadores addClusteredMarkers(googleMap) */

                // Wait for map to finish loading
                googleMap.awaitMapLoad()

                // Ensure all places are visible in the map
                val bounds = LatLngBounds.builder()
                //places.forEach { bounds.include(it.latLng) }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 20))
            }

        }
    }

    private fun setupObserver() {

    }

}