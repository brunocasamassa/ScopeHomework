package com.scope.application.screens.list.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import com.scope.application.domain.models.Vehicle

class CustomHorizontalCarsView(context: Context, val attributeSet: AttributeSet?) : HorizontalScrollView(context, attributeSet) {

    constructor(context: Context) : this(context, null)


    fun setVehicles(list:List<Vehicle>){
        list.forEach {  }

    }

}