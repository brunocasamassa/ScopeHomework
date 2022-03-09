package com.scope.application.screens.list.customviews

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.scope.application.R
import com.scope.application.databinding.ViewHorizontalListCarsBinding
import com.scope.application.databinding.ViewItemListCarBinding
import com.scope.application.domain.models.Vehicle
import com.scope.application.utils.show

class CarView(context: Context, attributeSet: AttributeSet?, val vehicle: Vehicle?) :
    ConstraintLayout(context, attributeSet) {

    constructor(context: Context, vehicle: Vehicle) : this(context, null, vehicle)
    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, null)
    constructor(context: Context) : this(context, null, null)

    private val binding: ViewItemListCarBinding = ViewItemListCarBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        with(binding) {
            vehicle?.let {
                carImg.background.setTint(Color.parseColor(it.color))
                make.text = it.make
                modelYear.text = "${it.model} - ${it.year}"
                baseCardView.show(true)
            }
        }

    }


}