package com.scope.application.screens.map.customviews

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.scope.application.R
import com.scope.application.databinding.DialogBottomSheetCarSelectedBinding
import com.scope.application.domain.vo.CarPointVO

class DialogCarView(context: Context, vehicleSelected:CarPointVO):ConstraintLayout(context) {


     var binding: DialogBottomSheetCarSelectedBinding = DialogBottomSheetCarSelectedBinding.inflate(
         LayoutInflater.from(context), this, true)


    init {
        with(binding) {
            Glide.with(carPhoto.context)
                .load(vehicleSelected.photo)
                .centerCrop()
                .error(R.drawable.ic_car)
                .into(carPhoto)

            vehicleName.text = "${vehicleSelected.make} - ${vehicleSelected.model}"
            vehicleAddress.text = "${vehicleSelected.address}"
            vehicleColor.background.setTint(Color.parseColor(vehicleSelected.color))


        }
    }


}