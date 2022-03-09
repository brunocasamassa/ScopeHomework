package com.scope.application.screens.map.customviews

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scope.application.R
import com.scope.application.databinding.DialogBottomSheetCarSelectedBinding
import com.scope.application.domain.vo.CarPointVO

class CarSelectedBottomDialog(val vehicleSelected: CarPointVO) : BottomSheetDialogFragment() {


    lateinit var binding: DialogBottomSheetCarSelectedBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogBottomSheetCarSelectedBinding.inflate(
            LayoutInflater.from(context), container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            Glide.with(carPhoto.context)
                .load(vehicleSelected.photo)
                .centerCrop()
                .error(R.drawable.ic_car)
                .into(carPhoto)

            vehicleName.text = "${vehicleSelected.make} - ${vehicleSelected.model}"
            vehicleAddress.text = "${vehicleSelected.address}"
            vehicleColor.background.setTint(Color.parseColor(vehicleSelected.color))

            buttonClose.setOnClickListener { this@CarSelectedBottomDialog.dismiss() }

        }
    }
}