package com.scope.application.screens.list.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import com.scope.application.databinding.ViewHorizontalListCarsBinding
import com.scope.application.databinding.ViewItemDriverBinding
import com.scope.application.domain.models.Vehicle

class HorizontalListCarsView(context: Context, val attributeSet: AttributeSet?) :
    HorizontalScrollView(context, attributeSet) {

    constructor(context: Context) : this(context, null)

    private val binding: ViewHorizontalListCarsBinding = ViewHorizontalListCarsBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setVehicles(list: List<Vehicle>) {
        setHorizontalScrollBarEnabled(false);
        binding.baseLinear.let {
            list.forEach {vehicle ->
                it.addView(CarView(context,vehicle))
            }

            it.addView(CarView(context))
            it.addView(CarView(context))
            it.addView(CarView(context))

        }

    }

}