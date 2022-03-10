package com.scope.application.screens.list.adapter


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.scope.application.R
import com.scope.application.databinding.ViewItemDriverBinding
import com.scope.application.domain.models.Vehicle
import com.scope.commons.extensions.show
import kotlin.properties.Delegates

class ItemDriverView(context: Context, attributeSet: AttributeSet?) :
    ConstraintLayout(context, attributeSet) {

    constructor(context: Context) : this(context, null)


    private val binding: ViewItemDriverBinding = ViewItemDriverBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var imageUrl by Delegates.observable<String?>(null) { _, _, newImage ->
        Glide.with(binding.imageDriver.context)
            .load(newImage)
            .centerCrop()
            .error(R.drawable.ic_close)
            .into(binding.imageDriver)
    }

    var nameDriver by Delegates.observable<String?>(null) { _, _, newTitle ->
        if (newTitle.isNullOrEmpty()) {
            binding.driverName.show(false)
        } else {
            with(binding.driverName) {
                text = newTitle
                show(true)
            }
        }
    }

    var vehicles: List<Vehicle>? by Delegates.observable(null) { _, _, newList ->
        newList?.let { binding.horizontalCarsList.setVehicles(it) }
    }




}


